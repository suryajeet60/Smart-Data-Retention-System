package com.FullStack.Smart_Data_Retention_System.service.impl;

import com.FullStack.Smart_Data_Retention_System.dto.AuditLogDTO;
import com.FullStack.Smart_Data_Retention_System.entity.AuditLog;
import com.FullStack.Smart_Data_Retention_System.entity.type.AuditActionType;
import com.FullStack.Smart_Data_Retention_System.exception.SmartDataRetentionException;
import com.FullStack.Smart_Data_Retention_System.mapper.AuditLogMapper;
import com.FullStack.Smart_Data_Retention_System.repository.AuditLogRepository;
import com.FullStack.Smart_Data_Retention_System.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuditLogServiceImpl implements AuditLogService
{

    private final AuditLogRepository auditLogRepository;

    @Override
    public AuditLogDTO getAuditLogById(Long auditLogId)
    {

        AuditLog auditLog = auditLogRepository.findById(auditLogId).orElseThrow(() -> new SmartDataRetentionException("Audit log not found."));

        return AuditLogMapper.toDTO(auditLog);
    }

    @Override
    public List<AuditLogDTO> getAllAuditLogs()
    {
        return AuditLogMapper.toDTOList(auditLogRepository.findAll());
    }

    @Override
    public Page<AuditLogDTO> getAllAuditLogs(Pageable pageable)
    {
        return auditLogRepository.findAll(pageable).map(AuditLogMapper::toDTO);
    }

    @Override
    public List<AuditLogDTO> getAuditLogsByAction(AuditActionType actionType)
    {
        return AuditLogMapper.toDTOList(auditLogRepository.findByActionType(actionType));
    }

    @Override
    public List<AuditLogDTO> getAuditLogsByTable(String tableName)
    {
        return AuditLogMapper.toDTOList(auditLogRepository.findByTableName(tableName));
    }

    @Override
    public List<AuditLogDTO> getAuditLogsByStatus(String status)
    {
        return AuditLogMapper.toDTOList(auditLogRepository.findByStatus(status));
    }

    @Override
    public List<AuditLogDTO> getAuditLogsByUser(Long userId)
    {
        return AuditLogMapper.toDTOList(auditLogRepository.findByUserId(userId));
    }

    @Override
    public List<AuditLogDTO> getAuditLogsBetween(LocalDateTime start, LocalDateTime end)
    {
        return AuditLogMapper.toDTOList(auditLogRepository.findByExecutedAtBetween(start, end));
    }

    @Override
    public List<AuditLogDTO> getAuditLogsAfter(LocalDateTime date)
    {
        return AuditLogMapper.toDTOList(auditLogRepository.findByExecutedAtAfter(date));
    }

    @Override
    public List<AuditLogDTO> getRecentAuditLogs()
    {
        return AuditLogMapper.toDTOList(auditLogRepository.getRecentAuditLogs());
    }
}
