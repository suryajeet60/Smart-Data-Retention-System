package com.FullStack.Smart_Data_Retention_System.mapper;

import com.FullStack.Smart_Data_Retention_System.dto.AuditLogDTO;
import com.FullStack.Smart_Data_Retention_System.entity.AuditLog;
import com.FullStack.Smart_Data_Retention_System.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class AuditLogMapper
{

    private AuditLogMapper()
    {
    }

    public static AuditLogDTO toDTO(AuditLog auditLog)
    {
        if (auditLog == null)
        {
            return null;
        }

        return AuditLogDTO.builder()
                .id(auditLog.getId())
                .actionType(auditLog.getActionType())
                .tableName(auditLog.getTableName())
                .affectedRecords(auditLog.getAffectedRecords())
                .status(auditLog.getStatus())
                .message(auditLog.getMessage())
                .executedAt(auditLog.getExecutedAt())
                .userId(auditLog.getUser() != null ? auditLog.getUser().getId() : null)
                .userName(auditLog.getUser() != null ? auditLog.getUser().getName() : null)
                .userEmail(auditLog.getUser() != null ? auditLog.getUser().getEmail() : null)
                .build();
    }

    public static AuditLog toEntity(AuditLogDTO dto, User user)
    {
        if (dto == null)
        {
            return null;
        }

        return AuditLog.builder()
                .id(dto.getId())
                .actionType(dto.getActionType())
                .tableName(dto.getTableName())
                .affectedRecords(dto.getAffectedRecords())
                .status(dto.getStatus())
                .message(dto.getMessage())
                .executedAt(dto.getExecutedAt())
                .user(user)
                .build();
    }

    public static void updateEntity(AuditLog entity, AuditLogDTO dto)
    {
        if (entity == null || dto == null)
        {
            return;
        }

        entity.setActionType(dto.getActionType());
        entity.setTableName(dto.getTableName());
        entity.setAffectedRecords(dto.getAffectedRecords());
        entity.setStatus(dto.getStatus());
        entity.setMessage(dto.getMessage());
        entity.setExecutedAt(dto.getExecutedAt());
    }

    public static List<AuditLogDTO> toDTOList(List<AuditLog> auditLogs)
    {
        return auditLogs.stream()
                .map(AuditLogMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<AuditLog> toEntityList(List<AuditLogDTO> dtos, User user)
    {
        return dtos.stream()
                .map(dto -> toEntity(dto, user))
                .collect(Collectors.toList());
    }

}
