package com.FullStack.Smart_Data_Retention_System.service;

import com.FullStack.Smart_Data_Retention_System.dto.RetentionRuleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RetentionRuleService
{

    // **CRUD**

    RetentionRuleDTO createRule(RetentionRuleDTO dto, Long userId);

    RetentionRuleDTO updateRule(Long ruleId, RetentionRuleDTO dto);

    void deleteRule(Long ruleId);

    // **Find**

    RetentionRuleDTO getRuleById(Long ruleId);

    RetentionRuleDTO getRuleByTableName(String tableName);

    List<RetentionRuleDTO> getAllRules();

    Page<RetentionRuleDTO> getAllRules(Pageable pageable);

    // **Active / Inactive**

    List<RetentionRuleDTO> getActiveRules();

    List<RetentionRuleDTO> getInactiveRules();

    RetentionRuleDTO activateRule(Long ruleId);

    RetentionRuleDTO deactivateRule(Long ruleId);

}
