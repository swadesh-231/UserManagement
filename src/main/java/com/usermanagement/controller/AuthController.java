package com.usermanagement.controller;

import com.usermanagement.dto.LoginRequest;
import com.usermanagement.dto.LoginResponse;
import com.usermanagement.dto.RegisterRequest;
import com.usermanagement.dto.UserResponse;
import com.usermanagement.entity.User;
import com.usermanagement.exception.ResourceNotFoundException;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.AuthService;
import com.usermanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.registerNormalUser(registerRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        LoginResponse response = authService.login(loginRequest);
        ResponseCookie cookie = ResponseCookie.from("Jwt",response.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 60)
                .sameSite("Strict")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString()).body(response.getUser());
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.ok().build();
    }
    @GetMapping("/getCurrentUser")
    public ResponseEntity<UserResponse> getCurrentLoginUser(Authentication authentication){
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = authentication.getName();
        UserResponse response = userService.findUserByUsername(username);
        return ResponseEntity.ok(response);
    }
}
