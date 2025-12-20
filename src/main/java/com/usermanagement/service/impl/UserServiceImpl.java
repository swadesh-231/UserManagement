package com.usermanagement.service.impl;

import com.usermanagement.dto.ChangePasswordDto;
import com.usermanagement.dto.UserRequest;
import com.usermanagement.dto.UserResponse;
import com.usermanagement.entity.User;
import com.usermanagement.exception.BadRequestException;
import com.usermanagement.exception.ResourceNotFoundException;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponse.class);
    }

    @Override
    public List<UserResponse> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user,UserResponse.class))
                .toList();
    }
    @Override
    public UserResponse findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("user","id",id));
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse findUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("user","username",username));
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("user","id",id));
        userRepository.delete(user);
    }
    @Override
    public UserResponse changePassword(Long id, ChangePasswordDto changePasswordDto) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("user","id",id));
        if (!passwordEncoder.matches(
                changePasswordDto.getCurrentPassword(),
                user.getPassword())) {
            throw new BadRequestException("Current password is incorrect");
        }
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())) {
            throw new BadRequestException("New Password and Confirm Password do not match");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponse.class);
    }
}
