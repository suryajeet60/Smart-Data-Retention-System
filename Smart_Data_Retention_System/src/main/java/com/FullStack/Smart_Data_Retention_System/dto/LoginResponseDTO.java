package com.FullStack.Smart_Data_Retention_System.dto;

import com.FullStack.Smart_Data_Retention_System.entity.type.RoleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO
{
    private Long userId;

    private String name;

    private String email;

    private RoleType role;

    private String accessToken;

    @Builder.Default
    private String tokenType = "Bearer";
}
