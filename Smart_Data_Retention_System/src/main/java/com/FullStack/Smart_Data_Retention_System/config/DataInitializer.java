package com.FullStack.Smart_Data_Retention_System.config;

import com.FullStack.Smart_Data_Retention_System.entity.User;
import com.FullStack.Smart_Data_Retention_System.entity.type.RoleType;
import com.FullStack.Smart_Data_Retention_System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner
{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args)
    {

        if (!userRepository.existsByEmail("admin@gmail.com"))
        {

            User admin = User.builder()
                    .name("Admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(RoleType.ADMIN)
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(admin);

            System.out.println("========================================");
            System.out.println("Default Admin Created");
            System.out.println("Email    : admin@gmail.com");
            System.out.println("Password : admin123");
            System.out.println("========================================");
        }

    }

}
