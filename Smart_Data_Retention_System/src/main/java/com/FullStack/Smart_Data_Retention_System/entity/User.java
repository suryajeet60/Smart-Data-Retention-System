package com.FullStack.Smart_Data_Retention_System.entity;

import com.FullStack.Smart_Data_Retention_System.entity.type.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User
{

    // **Columns** //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                                //Primary key.

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // **Relationships** //

    // one User -> multiple rules
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RetentionRule> retentionRules;

    // one User -> multiple logs.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AuditLog> auditLogs;
}
