package com.FullStack.Smart_Data_Retention_System.controller;

import com.FullStack.Smart_Data_Retention_System.dto.RetentionRuleDTO;
import com.FullStack.Smart_Data_Retention_System.dto.RuleExecutionDTO;
import com.FullStack.Smart_Data_Retention_System.service.RetentionExecutionService;
import com.FullStack.Smart_Data_Retention_System.service.RetentionRuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
@RequiredArgsConstructor
public class RetentionRuleController
{

    private final RetentionRuleService retentionRuleService;

    private final RetentionExecutionService retentionExecutionService;

    // Create Rule
    @PostMapping
    public ResponseEntity<RetentionRuleDTO> createRule(
            @Valid @RequestBody RetentionRuleDTO dto,
            @RequestParam Long userId)
    {

        RetentionRuleDTO rule = retentionRuleService.createRule(dto, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(rule);
    }

    // Update Rule
    @PutMapping("/{ruleId}")
    public ResponseEntity<RetentionRuleDTO> updateRule(
            @PathVariable Long ruleId,
            @Valid @RequestBody RetentionRuleDTO dto)
    {

        return ResponseEntity.ok(retentionRuleService.updateRule(ruleId, dto));
    }

    // Delete Rule
    @DeleteMapping("/{ruleId}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long ruleId)
    {
        retentionRuleService.deleteRule(ruleId);

        return ResponseEntity.noContent().build();
    }

    // Get Rule By Id
    @GetMapping("/{ruleId}")
    public ResponseEntity<RetentionRuleDTO> getRuleById(@PathVariable Long ruleId)
    {
        return ResponseEntity.ok(retentionRuleService.getRuleById(ruleId));
    }

    // Get Rule By Table Name
    @GetMapping("/table/{tableName}")
    public ResponseEntity<RetentionRuleDTO> getRuleByTable(@PathVariable String tableName)
    {
        return ResponseEntity.ok(retentionRuleService.getRuleByTableName(tableName));
    }

    // Get All Rules
    @GetMapping
    public ResponseEntity<List<RetentionRuleDTO>> getAllRules()
    {
        return ResponseEntity.ok(retentionRuleService.getAllRules());
    }

    // Get Rules With Pagination
    @GetMapping("/page")
    public ResponseEntity<Page<RetentionRuleDTO>> getAllRules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(retentionRuleService.getAllRules(pageable));
    }

    // Active Rules
    @GetMapping("/active")
    public ResponseEntity<List<RetentionRuleDTO>> getActiveRules()
    {

        return ResponseEntity.ok(retentionRuleService.getActiveRules());

    }

    // Inactive Rules
    @GetMapping("/inactive")
    public ResponseEntity<List<RetentionRuleDTO>> getInactiveRules()
    {
        return ResponseEntity.ok(retentionRuleService.getInactiveRules());
    }

    // Activate Rule
    @PatchMapping("/{ruleId}/activate")
    public ResponseEntity<RetentionRuleDTO> activateRule(@PathVariable Long ruleId)
    {
        return ResponseEntity.ok(retentionRuleService.activateRule(ruleId));
    }

    // Deactivate Rule
    @PatchMapping("/{ruleId}/deactivate")
    public ResponseEntity<RetentionRuleDTO> deactivateRule(@PathVariable Long ruleId)
    {

        return ResponseEntity.ok(retentionRuleService.deactivateRule(ruleId));
    }

    // Execute One Rule
    @PostMapping("/{ruleId}/execute")
    public ResponseEntity<RuleExecutionDTO> executeRule(@PathVariable Long ruleId)
    {
        return ResponseEntity.ok(retentionExecutionService.executeRule(ruleId));
    }

    // Execute All Active Rules
    @PostMapping("/execute")
    public ResponseEntity<List<RuleExecutionDTO>> executeAllRules()
    {
        return ResponseEntity.ok(retentionExecutionService.executeAllActiveRules());
    }

    // Count Expired Records
    @GetMapping("/{ruleId}/expired-records")
    public ResponseEntity<Long> countExpiredRecords(@PathVariable Long ruleId)
    {
        return ResponseEntity.ok(retentionExecutionService.countExpiredRecords(ruleId));
    }
}
