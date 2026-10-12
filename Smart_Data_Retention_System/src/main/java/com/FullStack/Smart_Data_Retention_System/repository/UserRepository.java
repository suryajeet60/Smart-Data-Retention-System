package com.FullStack.Smart_Data_Retention_System.repository;

import com.FullStack.Smart_Data_Retention_System.entity.User;
import com.FullStack.Smart_Data_Retention_System.entity.type.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{

    // **Authentication** //

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    // **Search** //

    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<User> findByRole(RoleType role, Pageable pageable);

    // **Dashboard** //

    @Query("SELECT COUNT(u) FROM User u")
    long countTotalUsers();

}
