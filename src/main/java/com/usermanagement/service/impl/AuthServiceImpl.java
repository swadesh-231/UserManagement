package com.usermanagement.service.impl;

import com.usermanagement.dto.LoginRequest;
import com.usermanagement.dto.LoginResponse;
import com.usermanagement.dto.RegisterRequest;
import com.usermanagement.dto.UserResponse;
import com.usermanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Override
    public UserResponse registerNormalUser(RegisterRequest registerRequest) {
        return null;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }
}
