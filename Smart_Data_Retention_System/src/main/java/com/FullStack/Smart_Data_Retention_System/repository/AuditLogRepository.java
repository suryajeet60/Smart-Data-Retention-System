package com.FullStack.Smart_Data_Retention_System.repository;

import com.FullStack.Smart_Data_Retention_System.entity.AuditLog;
import com.FullStack.Smart_Data_Retention_System.entity.type.AuditActionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long>
{

    // **Filters**

    List<AuditLog> findByActionType(AuditActionType actionType);

    List<AuditLog> findByTableName(String tableName);

    List<AuditLog> findByStatus(String status);

    List<AuditLog> findByUserId(Long userId);

    // **Date Queries**

    List<AuditLog> findByExecutedAtBetween(LocalDateTime start, LocalDateTime end);

    List<AuditLog> findByExecutedAtAfter(LocalDateTime date);

    // **Pagination**

    Page<AuditLog> findAll(Pageable pageable);

    Page<AuditLog> findByActionType(AuditActionType actionType, Pageable pageable);

    // **Dashboard**

    @Query("SELECT COUNT(a) FROM AuditLog a")
    long countTotalAuditLogs();

    @Query("""
            SELECT COUNT(a)
            FROM AuditLog a
            WHERE a.actionType =
            com.FullStack.Smart_Data_Retention_System.entity.type.AuditActionType.EXECUTE_RETENTION
            """)
    long countRetentionExecutions();

    // **Recent Logs**

    List<AuditLog> findTop10ByOrderByExecutedAtDesc();

    // **Native Query**

    @Query(value = """
            SELECT *
            FROM audit_logs
            ORDER BY executed_at DESC
            LIMIT 5
            """, nativeQuery = true)
    List<AuditLog> getRecentAuditLogs();

}
