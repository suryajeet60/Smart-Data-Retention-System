package com.FullStack.Smart_Data_Retention_System.dto;

import com.FullStack.Smart_Data_Retention_System.entity.type.AuditActionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogDTO                                        // carries data from backend → frontend
{

    private Long id;

    private AuditActionType actionType;

    private String tableName;

    private Long affectedRecords;

    private String status;                                       // SUCCESS / FAILED

    private String message;

    private LocalDateTime executedAt;

    // User information
    private Long userId;

    private String userName;

    private String userEmail;

}
