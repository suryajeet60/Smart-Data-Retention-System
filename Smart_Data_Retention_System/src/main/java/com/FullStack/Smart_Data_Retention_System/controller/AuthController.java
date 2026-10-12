package com.FullStack.Smart_Data_Retention_System.controller;

import com.FullStack.Smart_Data_Retention_System.dto.LoginRequestDTO;
import com.FullStack.Smart_Data_Retention_System.dto.LoginResponseDTO;
import com.FullStack.Smart_Data_Retention_System.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController
{

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO)
    {

        LoginResponseDTO response = authService.login(loginRequestDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
