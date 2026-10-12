package com.FullStack.Smart_Data_Retention_System.service;

import com.FullStack.Smart_Data_Retention_System.dto.RuleExecutionDTO;

import java.util.List;

public interface RetentionExecutionService
{

    RuleExecutionDTO executeRule(Long ruleId);

    List<RuleExecutionDTO> executeAllActiveRules();

    Long countExpiredRecords(Long ruleId);
}
