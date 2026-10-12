package com.FullStack.Smart_Data_Retention_System.service.impl;

import com.FullStack.Smart_Data_Retention_System.dto.RuleExecutionDTO;
import com.FullStack.Smart_Data_Retention_System.entity.AuditLog;
import com.FullStack.Smart_Data_Retention_System.entity.RetentionRule;
import com.FullStack.Smart_Data_Retention_System.entity.type.AuditActionType;
import com.FullStack.Smart_Data_Retention_System.entity.type.RetentionActionType;
import com.FullStack.Smart_Data_Retention_System.exception.SmartDataRetentionException;
import com.FullStack.Smart_Data_Retention_System.repository.AuditLogRepository;
import com.FullStack.Smart_Data_Retention_System.repository.RetentionRuleRepository;
import com.FullStack.Smart_Data_Retention_System.service.ArchiveService;
import com.FullStack.Smart_Data_Retention_System.service.DatabaseExplorerService;
import com.FullStack.Smart_Data_Retention_System.service.RetentionExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RetentionExecutionServiceImpl implements RetentionExecutionService
{

    private final RetentionRuleRepository retentionRuleRepository;

    private final AuditLogRepository auditLogRepository;

    private final DatabaseExplorerService databaseExplorerService;

    private final ArchiveService archiveService;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public RuleExecutionDTO executeRule(Long ruleId)
    {

        RetentionRule rule = retentionRuleRepository.findById(ruleId).orElseThrow(() -> new SmartDataRetentionException("Retention rule not found."));

        if (!Boolean.TRUE.equals(rule.getActive()))
        {
            throw new SmartDataRetentionException("Retention rule is inactive.");
        }

        if (!databaseExplorerService.tableExists(rule.getTableName()))
        {
            throw new SmartDataRetentionException("Table not found: " + rule.getTableName());
        }

        LocalDateTime expiryDate = LocalDateTime.now().minusDays(rule.getRetentionPeriodDays());

        String countSql =
                "SELECT COUNT(*) FROM " +
                        rule.getTableName() +
                        " WHERE " +
                        rule.getDateColumn() +
                        " < ?";

        Long affectedRecords = jdbcTemplate.queryForObject(countSql, Long.class, Timestamp.valueOf(expiryDate));

        if (affectedRecords == null)
        {
            affectedRecords = 0L;
        }

        String status = "SUCCESS";
        String message;

        try
        {

            if (rule.getAction() == RetentionActionType.DELETE)
            {

                String deleteSql =
                        "DELETE FROM " +
                                rule.getTableName() +
                                " WHERE " +
                                rule.getDateColumn() +
                                " < ?";

                jdbcTemplate.update(deleteSql, Timestamp.valueOf(expiryDate));

                message = affectedRecords + " expired records deleted.";

            }
            else
            {
                affectedRecords = archiveService.archiveExpiredRecords(rule);

                message = affectedRecords + " expired records archived.";

                status = "SUCCESS";
            }
        }
        catch (Exception ex)
        {
            status = "FAILED";

            message = ex.getMessage();
        }

        saveAudit(rule, affectedRecords, status, message);

        return RuleExecutionDTO.builder()
                .ruleId(rule.getId())
                .tableName(rule.getTableName())
                .action(rule.getAction().name())
                .affectedRecords(affectedRecords)
                .status(status)
                .message(message)
                .executedAt(LocalDateTime.now())
                .build();

    }

    @Override
    public List<RuleExecutionDTO> executeAllActiveRules()
    {

        List<RuleExecutionDTO> result = new ArrayList<>();

        List<RetentionRule> rules = retentionRuleRepository.findByActiveTrue();

        for (RetentionRule rule : rules)
        {
            result.add(executeRule(rule.getId()));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Long countExpiredRecords(Long ruleId)
    {

        RetentionRule rule = retentionRuleRepository.findById(ruleId).orElseThrow(() -> new SmartDataRetentionException("Retention rule not found."));

        LocalDateTime expiry = LocalDateTime.now().minusDays(rule.getRetentionPeriodDays());

        String sql =
                "SELECT COUNT(*) FROM " +
                        rule.getTableName() +
                        " WHERE " +
                        rule.getDateColumn() +
                        " < ?";

        Long count = jdbcTemplate.queryForObject(sql, Long.class, Timestamp.valueOf(expiry));

        return count == null ? 0L : count;
    }

    private void saveAudit(RetentionRule rule, Long affectedRecords, String status, String message)
    {

        AuditLog log = AuditLog.builder()
                .actionType(AuditActionType.EXECUTE_RETENTION)
                .tableName(rule.getTableName())
                .affectedRecords(affectedRecords)
                .status(status)
                .message(message)
                .executedAt(LocalDateTime.now())
                .user(rule.getCreatedBy())
                .build();

        auditLogRepository.save(log);
    }
}
