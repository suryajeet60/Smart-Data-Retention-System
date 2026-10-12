package com.FullStack.Smart_Data_Retention_System.service.impl;

import com.FullStack.Smart_Data_Retention_System.dto.RetentionRuleDTO;
import com.FullStack.Smart_Data_Retention_System.entity.AuditLog;
import com.FullStack.Smart_Data_Retention_System.entity.RetentionRule;
import com.FullStack.Smart_Data_Retention_System.entity.User;
import com.FullStack.Smart_Data_Retention_System.entity.type.AuditActionType;
import com.FullStack.Smart_Data_Retention_System.exception.SmartDataRetentionException;
import com.FullStack.Smart_Data_Retention_System.mapper.RetentionRuleMapper;
import com.FullStack.Smart_Data_Retention_System.repository.AuditLogRepository;
import com.FullStack.Smart_Data_Retention_System.repository.RetentionRuleRepository;
import com.FullStack.Smart_Data_Retention_System.repository.UserRepository;
import com.FullStack.Smart_Data_Retention_System.service.RetentionRuleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RetentionRuleServiceImpl implements RetentionRuleService
{

    // this 3 are Repository dependencies.
    private final RetentionRuleRepository retentionRuleRepository;
    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;

    @Override
    public RetentionRuleDTO createRule(RetentionRuleDTO dto, Long userId)
    {

        if (retentionRuleRepository.existsByTableName(dto.getTableName()))
        {
            throw new SmartDataRetentionException("Retention rule already exists for table : " + dto.getTableName());
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        RetentionRule rule = RetentionRuleMapper.toEntity(dto, user);

        rule.setCreatedAt(LocalDateTime.now());

        if (rule.getActive() == null)
        {
            rule.setActive(true);
        }

        RetentionRule saved = retentionRuleRepository.save(rule);

        saveAudit(user,
                AuditActionType.CREATE_RULE,
                saved.getTableName(),
                "SUCCESS",
                "Retention rule created.");

        return RetentionRuleMapper.toDTO(saved);
    }

    @Override
    public RetentionRuleDTO updateRule(Long ruleId, RetentionRuleDTO dto)
    {

        RetentionRule rule = retentionRuleRepository.findById(ruleId).orElseThrow(() -> new SmartDataRetentionException("Retention rule not found."));

        RetentionRuleMapper.updateEntity(rule, dto);

        RetentionRule updated = retentionRuleRepository.save(rule);

        saveAudit(rule.getCreatedBy(),
                AuditActionType.UPDATE_RULE,
                updated.getTableName(),
                "SUCCESS",
                "Retention rule updated.");

        return RetentionRuleMapper.toDTO(updated);
    }

    @Override
    public void deleteRule(Long ruleId)
    {

        RetentionRule rule = retentionRuleRepository.findById(ruleId).orElseThrow(() -> new SmartDataRetentionException("Retention rule not found."));

        retentionRuleRepository.delete(rule);

        saveAudit(rule.getCreatedBy(),
                AuditActionType.DELETE_RULE,
                rule.getTableName(),
                "SUCCESS",
                "Retention rule deleted.");
    }

    @Override
    @Transactional(readOnly = true)
    public RetentionRuleDTO getRuleById(Long ruleId)
    {

        return retentionRuleRepository.findById(ruleId)
                .map(RetentionRuleMapper::toDTO)
                .orElseThrow(() -> new SmartDataRetentionException("Retention rule not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public RetentionRuleDTO getRuleByTableName(String tableName)
    {

        return retentionRuleRepository.findByTableName(tableName)
                .map(RetentionRuleMapper::toDTO)
                .orElseThrow(() -> new SmartDataRetentionException("Retention rule not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RetentionRuleDTO> getAllRules()
    {
        return RetentionRuleMapper.toDTOList(retentionRuleRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RetentionRuleDTO> getAllRules(Pageable pageable)
    {
        return retentionRuleRepository.findAll(pageable).map(RetentionRuleMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RetentionRuleDTO> getActiveRules()
    {
        return RetentionRuleMapper.toDTOList(retentionRuleRepository.findByActiveTrue());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RetentionRuleDTO> getInactiveRules()
    {

        return RetentionRuleMapper.toDTOList(retentionRuleRepository.findByActiveFalse());
    }

    @Override
    public RetentionRuleDTO activateRule(Long ruleId)
    {

        RetentionRule rule = retentionRuleRepository.findById(ruleId).orElseThrow(() -> new SmartDataRetentionException("Retention rule not found."));

        rule.setActive(true);

        retentionRuleRepository.save(rule);

        saveAudit(rule.getCreatedBy(),
                AuditActionType.ACTIVATE_RULE,
                rule.getTableName(),
                "SUCCESS",
                "Rule activated.");

        return RetentionRuleMapper.toDTO(rule);
    }

    @Override
    public RetentionRuleDTO deactivateRule(Long ruleId)
    {

        RetentionRule rule = retentionRuleRepository.findById(ruleId).orElseThrow(() -> new SmartDataRetentionException("Retention rule not found."));

        rule.setActive(false);

        retentionRuleRepository.save(rule);

        saveAudit(rule.getCreatedBy(),
                AuditActionType.DEACTIVATE_RULE,
                rule.getTableName(),
                "SUCCESS",
                "Rule deactivated.");

        return RetentionRuleMapper.toDTO(rule);
    }

    private void saveAudit(User user, AuditActionType action, String table, String status, String message)
    {

        AuditLog log = AuditLog.builder()
                .actionType(action)
                .tableName(table)
                .affectedRecords(0L)
                .status(status)
                .message(message)
                .executedAt(LocalDateTime.now())
                .user(user)
                .build();

        auditLogRepository.save(log);
    }
}
