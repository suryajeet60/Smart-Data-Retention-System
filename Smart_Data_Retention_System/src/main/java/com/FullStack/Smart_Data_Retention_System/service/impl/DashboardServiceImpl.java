package com.FullStack.Smart_Data_Retention_System.service.impl;

import com.FullStack.Smart_Data_Retention_System.dto.DashboardDTO;
import com.FullStack.Smart_Data_Retention_System.repository.AuditLogRepository;
import com.FullStack.Smart_Data_Retention_System.repository.RetentionRuleRepository;
import com.FullStack.Smart_Data_Retention_System.repository.UserRepository;
import com.FullStack.Smart_Data_Retention_System.service.DashboardService;
import com.FullStack.Smart_Data_Retention_System.service.DatabaseExplorerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService
{

    private final UserRepository userRepository;

    private final RetentionRuleRepository retentionRuleRepository;

    private final AuditLogRepository auditLogRepository;

    private final DatabaseExplorerService databaseExplorerService;

    @Override
    public DashboardDTO getDashboard()
    {

        Long totalUsers = userRepository.countTotalUsers();

        Long totalRules = retentionRuleRepository.countTotalRules();

        Long activeRules = retentionRuleRepository.countActiveRules();

        Long inactiveRules = retentionRuleRepository.countInactiveRules();

        Long totalAuditLogs = auditLogRepository.countTotalAuditLogs();

        Long retentionExecutions = auditLogRepository.countRetentionExecutions();

        Integer totalTables = databaseExplorerService.getAllTables().size();

        Long successfulExecutions =
                auditLogRepository.findByStatus("SUCCESS")
                        .stream()
                        .filter(log ->
                                log.getActionType().name()
                                        .equals("EXECUTE_RETENTION"))
                        .count();

        Long failedExecutions =
                auditLogRepository.findByStatus("FAILED")
                        .stream()
                        .filter(log ->
                                log.getActionType().name()
                                        .equals("EXECUTE_RETENTION"))
                        .count();

        return DashboardDTO.builder()
                .totalUsers(totalUsers)
                .totalRules(totalRules)
                .activeRules(activeRules)
                .inactiveRules(inactiveRules)
                .totalAuditLogs(totalAuditLogs)
                .retentionExecutions(retentionExecutions)
                .totalTables(totalTables)
                .successfulExecutions(successfulExecutions)
                .failedExecutions(failedExecutions)
                .build();

    }
}
