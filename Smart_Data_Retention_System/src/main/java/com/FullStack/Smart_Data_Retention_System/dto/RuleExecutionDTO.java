package com.FullStack.Smart_Data_Retention_System.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleExecutionDTO
{

    private Long ruleId;

    private String tableName;

    private String action;

    private Long affectedRecords;

    private String status;

    private String message;

    private LocalDateTime executedAt;

}
