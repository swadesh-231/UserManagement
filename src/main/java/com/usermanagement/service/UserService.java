package com.usermanagement.service;

import com.usermanagement.dto.ChangePasswordDto;
import com.usermanagement.dto.UserRequest;
import com.usermanagement.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse updateUser(Long userId, UserRequest userRequest);
    List<UserResponse> findAllUsers();
    UserResponse findUserById(Long id);
    UserResponse findUserByUsername(String username);
    void deleteUser(Long id);
    UserResponse changePassword(Long id, ChangePasswordDto changePasswordDto);
}
