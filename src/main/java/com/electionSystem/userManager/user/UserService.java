package com.electionSystem.userManager.user;

import com.electionSystem.userManager.user.dto.UserRegistrationReq;
import com.electionSystem.userManager.user.dto.UserResponse;
import com.electionSystem.userManager.user.dto.UserUpdateReq;

import java.util.List;

public interface UserService {
    UserResponse register(UserRegistrationReq userReq);

    UserResponse me();

    List<UserResponse> getAllUsers();

    UserResponse editUser(UserUpdateReq updateReq);

    Users getUserByUsername(String email);

    Users getUserById(Long userId);

    void deleteUser(Long id);
}
