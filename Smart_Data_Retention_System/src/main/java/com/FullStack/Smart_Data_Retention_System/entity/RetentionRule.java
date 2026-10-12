package com.FullStack.Smart_Data_Retention_System.entity;

import com.FullStack.Smart_Data_Retention_System.entity.type.RetentionActionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "retention_rules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RetentionRule
{

    // **Columns** //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)                 //Primary Key.
    private Long id;

    @Column(nullable=false)
    private String tableName;

    @Column(nullable = false)
    private String dateColumn;

    @Column(nullable = false)
    private Integer retentionPeriodDays;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RetentionActionType action;

    @Column(nullable = false)
    private Boolean active;                          // true==1 ; false==0

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // **Relationship** //

    // many rules --> creates one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="created_by")
    private User createdBy;
}
