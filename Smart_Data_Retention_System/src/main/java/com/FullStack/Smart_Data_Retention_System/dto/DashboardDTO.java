package com.FullStack.Smart_Data_Retention_System.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDTO                                  // carries data from backend → frontend
{

    // **Users**
    private Long totalUsers;

    // Rules
    private Long totalRules;
    private Long activeRules;
    private Long inactiveRules;

    // Audit
    private Long totalAuditLogs;
    private Long retentionExecutions;

    // Database
    private Integer totalTables;

    // Execution
    private Long successfulExecutions;
    private Long failedExecutions;

}
