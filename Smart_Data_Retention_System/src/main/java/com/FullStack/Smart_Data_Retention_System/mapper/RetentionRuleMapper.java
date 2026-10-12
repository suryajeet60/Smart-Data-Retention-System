package com.FullStack.Smart_Data_Retention_System.mapper;

import com.FullStack.Smart_Data_Retention_System.dto.RetentionRuleDTO;
import com.FullStack.Smart_Data_Retention_System.entity.RetentionRule;
import com.FullStack.Smart_Data_Retention_System.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class RetentionRuleMapper
{

    private RetentionRuleMapper()
    {
    }

    public static RetentionRuleDTO toDTO(RetentionRule rule)
    {
        if (rule == null)
        {
            return null;
        }

        return RetentionRuleDTO.builder()
                .id(rule.getId())
                .tableName(rule.getTableName())
                .dateColumn(rule.getDateColumn())
                .retentionPeriodDays(rule.getRetentionPeriodDays())
                .action(rule.getAction())
                .active(rule.getActive())
                .createdAt(rule.getCreatedAt())
                .createdById(rule.getCreatedBy() != null ? rule.getCreatedBy().getId() : null)
                .createdByName(rule.getCreatedBy() != null ? rule.getCreatedBy().getName() : null)
                .build();
    }

    public static RetentionRule toEntity(RetentionRuleDTO dto, User createdBy)
    {
        if (dto == null)
        {
            return null;
        }

        return RetentionRule.builder()
                .id(dto.getId())
                .tableName(dto.getTableName())
                .dateColumn(dto.getDateColumn())
                .retentionPeriodDays(dto.getRetentionPeriodDays())
                .action(dto.getAction())
                .active(dto.getActive())
                .createdAt(dto.getCreatedAt())
                .createdBy(createdBy)
                .build();
    }

    public static void updateEntity(RetentionRule entity, RetentionRuleDTO dto)
    {
        if (entity == null || dto == null)
        {
            return;
        }

        entity.setTableName(dto.getTableName());
        entity.setDateColumn(dto.getDateColumn());
        entity.setRetentionPeriodDays(dto.getRetentionPeriodDays());
        entity.setAction(dto.getAction());

        if (dto.getActive() != null)
        {
            entity.setActive(dto.getActive());
        }
    }

    public static List<RetentionRuleDTO> toDTOList(List<RetentionRule> rules)
    {
        return rules.stream()
                .map(RetentionRuleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<RetentionRule> toEntityList(List<RetentionRuleDTO> dtos, User createdBy)
    {
        return dtos.stream()
                .map(dto -> toEntity(dto, createdBy))
                .collect(Collectors.toList());
    }

}
