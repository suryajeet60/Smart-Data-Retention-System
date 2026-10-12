package com.FullStack.Smart_Data_Retention_System.service.impl;

import com.FullStack.Smart_Data_Retention_System.dto.LoginRequestDTO;
import com.FullStack.Smart_Data_Retention_System.dto.LoginResponseDTO;
import com.FullStack.Smart_Data_Retention_System.entity.User;
import com.FullStack.Smart_Data_Retention_System.exception.SmartDataRetentionException;
import com.FullStack.Smart_Data_Retention_System.repository.UserRepository;
import com.FullStack.Smart_Data_Retention_System.security.JwtTokenProvider;
import com.FullStack.Smart_Data_Retention_System.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService
{

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO)
    {

        try
        {
            // Authenticate username & password
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        }
        catch (BadCredentialsException ex)
        {
            throw new SmartDataRetentionException("Invalid email or password.");
        }

        User user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new SmartDataRetentionException("User not found."));

        String token = jwtTokenProvider.generateToken(user);

        return LoginResponseDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .accessToken(token)
                .build();

    }
}
