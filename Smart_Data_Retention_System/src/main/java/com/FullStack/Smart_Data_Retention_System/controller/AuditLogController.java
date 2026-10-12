package com.FullStack.Smart_Data_Retention_System.controller;

import com.FullStack.Smart_Data_Retention_System.dto.AuditLogDTO;
import com.FullStack.Smart_Data_Retention_System.entity.type.AuditActionType;
import com.FullStack.Smart_Data_Retention_System.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
public class AuditLogController
{

    private final AuditLogService auditLogService;

    // Get Audit Log By Id
    @GetMapping("/{auditLogId}")
    public ResponseEntity<AuditLogDTO> getAuditLogById(@PathVariable Long auditLogId)
    {
        return ResponseEntity.ok(auditLogService.getAuditLogById(auditLogId));
    }

    // Get All Audit Logs
    @GetMapping
    public ResponseEntity<List<AuditLogDTO>> getAllAuditLogs()
    {
        return ResponseEntity.ok(auditLogService.getAllAuditLogs());
    }

    // Get Audit Logs With Pagination
    @GetMapping("/page")
    public ResponseEntity<Page<AuditLogDTO>> getAuditLogsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(auditLogService.getAllAuditLogs(pageable));
    }

    // Filter By Action
    @GetMapping("/action/{actionType}")
    public ResponseEntity<List<AuditLogDTO>> getAuditLogsByAction(@PathVariable AuditActionType actionType)
    {
        return ResponseEntity.ok(auditLogService.getAuditLogsByAction(actionType));
    }

    // Filter By Table Name
    @GetMapping("/table/{tableName}")
    public ResponseEntity<List<AuditLogDTO>> getAuditLogsByTable(@PathVariable String tableName)
    {
        return ResponseEntity.ok(auditLogService.getAuditLogsByTable(tableName));
    }

    // Filter By Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AuditLogDTO>> getAuditLogsByStatus(@PathVariable String status)
    {
        return ResponseEntity.ok(auditLogService.getAuditLogsByStatus(status));
    }

    // Filter By User
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLogDTO>> getAuditLogsByUser(@PathVariable Long userId)
    {
        return ResponseEntity.ok(auditLogService.getAuditLogsByUser(userId));
    }

    // Filter Between Dates
    @GetMapping("/between")
    public ResponseEntity<List<AuditLogDTO>> getAuditLogsBetween(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end)
    {

        return ResponseEntity.ok(auditLogService.getAuditLogsBetween(start, end));
    }

    // Filter After Date
    @GetMapping("/after")
    public ResponseEntity<List<AuditLogDTO>> getAuditLogsAfter(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime date)
    {

        return ResponseEntity.ok(auditLogService.getAuditLogsAfter(date));
    }

    // Recent Audit Logs
    @GetMapping("/recent")
    public ResponseEntity<List<AuditLogDTO>> getRecentAuditLogs()
    {
        return ResponseEntity.ok(auditLogService.getRecentAuditLogs());
    }
}
