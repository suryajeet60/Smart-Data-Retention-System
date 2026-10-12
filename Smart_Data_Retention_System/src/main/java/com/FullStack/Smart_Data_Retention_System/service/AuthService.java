package com.FullStack.Smart_Data_Retention_System.service;

import com.FullStack.Smart_Data_Retention_System.dto.LoginRequestDTO;
import com.FullStack.Smart_Data_Retention_System.dto.LoginResponseDTO;

public interface AuthService
{
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

}
