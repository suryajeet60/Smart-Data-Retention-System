package com.FullStack.Smart_Data_Retention_System.entity;

import com.FullStack.Smart_Data_Retention_System.entity.type.AuditActionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog
{

    // **Columns** //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)           //Primary Key
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditActionType actionType;

    @Column(nullable = false)
    private String tableName;

    private Long affectedRecords;

    private String status;

    @Column(length = 1000)
    private String message;

    @Column(nullable = false)
    private LocalDateTime executedAt;

    // **Relationships** //

    // many logs --> one User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")                     //FK
    private User user;
}
