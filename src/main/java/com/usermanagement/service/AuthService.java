package com.usermanagement.service;

import com.usermanagement.dto.*;


public interface AuthService {
    UserResponse registerNormalUser(RegisterRequest registerRequest);
    LoginResponse login( LoginRequest loginRequest);
}
