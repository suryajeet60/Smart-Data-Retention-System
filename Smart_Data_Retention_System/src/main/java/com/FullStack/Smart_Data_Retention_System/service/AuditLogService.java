package com.FullStack.Smart_Data_Retention_System.service;

import com.FullStack.Smart_Data_Retention_System.dto.AuditLogDTO;
import com.FullStack.Smart_Data_Retention_System.entity.type.AuditActionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogService
{

    // Find
    AuditLogDTO getAuditLogById(Long auditLogId);

    List<AuditLogDTO> getAllAuditLogs();

    Page<AuditLogDTO> getAllAuditLogs(Pageable pageable);

    // Filters
    List<AuditLogDTO> getAuditLogsByAction(AuditActionType actionType);

    List<AuditLogDTO> getAuditLogsByTable(String tableName);

    List<AuditLogDTO> getAuditLogsByStatus(String status);

    List<AuditLogDTO> getAuditLogsByUser(Long userId);

    // Date Filters
    List<AuditLogDTO> getAuditLogsBetween(LocalDateTime start, LocalDateTime end);

    List<AuditLogDTO> getAuditLogsAfter(LocalDateTime date);

    // Recent Logs
    List<AuditLogDTO> getRecentAuditLogs();
}
