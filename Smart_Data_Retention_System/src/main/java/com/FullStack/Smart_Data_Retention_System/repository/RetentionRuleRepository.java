package com.FullStack.Smart_Data_Retention_System.repository;

import com.FullStack.Smart_Data_Retention_System.entity.RetentionRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RetentionRuleRepository extends JpaRepository<RetentionRule, Long>
{

    // **Find Rule**

    Optional<RetentionRule> findByTableName(String tableName);

    boolean existsByTableName(String tableName);

    // **Active / Inactive Rules**

    List<RetentionRule> findByActiveTrue();

    List<RetentionRule> findByActiveFalse();

    Page<RetentionRule> findByActiveTrue(Pageable pageable);

    // **Dashboard**

    @Query("SELECT COUNT(r) FROM RetentionRule r")
    long countTotalRules();

    @Query("SELECT COUNT(r) FROM RetentionRule r WHERE r.active = true")
    long countActiveRules();

    @Query("SELECT COUNT(r) FROM RetentionRule r WHERE r.active = false")
    long countInactiveRules();

}
