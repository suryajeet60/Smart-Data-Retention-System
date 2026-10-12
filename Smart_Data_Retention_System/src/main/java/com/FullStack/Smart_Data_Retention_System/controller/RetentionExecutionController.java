package com.FullStack.Smart_Data_Retention_System.controller;

import com.FullStack.Smart_Data_Retention_System.dto.RuleExecutionDTO;
import com.FullStack.Smart_Data_Retention_System.service.RetentionExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/retention-execution")
@RequiredArgsConstructor
public class RetentionExecutionController
{

    private final RetentionExecutionService retentionExecutionService;

    // Execute Single Retention Rule
    @PostMapping("/rules/{ruleId}/execute")
    public ResponseEntity<RuleExecutionDTO> executeRule(@PathVariable Long ruleId)
    {

        RuleExecutionDTO response = retentionExecutionService.executeRule(ruleId);

        return ResponseEntity.ok(response);
    }

    // Execute All Active Retention Rules
    @PostMapping("/rules/execute-all")
    public ResponseEntity<List<RuleExecutionDTO>> executeAllActiveRules()
    {

        List<RuleExecutionDTO> response = retentionExecutionService.executeAllActiveRules();

        return ResponseEntity.ok(response);
    }

    // Preview Expired Records
    @GetMapping("/rules/{ruleId}/expired-records")
    public ResponseEntity<Long> countExpiredRecords(@PathVariable Long ruleId)
    {

        Long count = retentionExecutionService.countExpiredRecords(ruleId);

        return ResponseEntity.ok(count);
    }
}
