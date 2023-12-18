package com.electionSystem.userManager.account;

import com.electionSystem.exceptions.customExceptions.BadRequestException;
import com.electionSystem.exceptions.customExceptions.ForbiddenException;
import com.electionSystem.userManager.account.dto.ChangePassword;
import com.electionSystem.userManager.account.dto.ResetPassword;
import com.electionSystem.userManager.user.UserRepository;
import com.electionSystem.userManager.user.UserService;
import com.electionSystem.userManager.user.Users;
import com.electionSystem.utils.ApiResponse;
import com.electionSystem.utils.CurrentlyLoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CurrentlyLoggedInUser currentlyLoggedInUser;
    private final UserService userService;
    private final CurrentlyLoggedInUser loggedInUser;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ApiResponse> changePassword(ChangePassword changePassword) {
        Users user = currentlyLoggedInUser.getUser();
        validateOldPassword(user, changePassword.getOldPassword());
        user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        userRepository.save(user);

        return ApiResponse.success("Password Changed Successfully!");
    }

    // this method for admin.
    @Override
    @Transactional(rollbackFor = Exception.class)
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> resetPassword(String email, ResetPassword resetPassword) {
        Users inUser = loggedInUser.getUser();

        if (!inUser.getRole().getRoleName().equalsIgnoreCase("ADMIN"))
            throw new ForbiddenException("Access Denied: Only administrators are authorized to reset password.");

        Users user = userService.getUserByUsername(email);

        user.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
        userRepository.save(user);

        return ApiResponse.success("Password has been reset successfully!");
    }

    private void validateOldPassword(Users user, String oldPassword) {
        boolean isPasswordMatch = passwordEncoder.matches(oldPassword, user.getPassword());
        if (!isPasswordMatch)
            throw new BadRequestException("Incorrect old Password!");
    }

}
