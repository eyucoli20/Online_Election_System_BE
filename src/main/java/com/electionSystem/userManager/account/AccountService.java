package com.electionSystem.userManager.account;

import com.electionSystem.userManager.account.dto.ChangePassword;
import com.electionSystem.userManager.account.dto.ResetPassword;
import com.electionSystem.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    ResponseEntity<ApiResponse> changePassword(ChangePassword changePassword);

    ResponseEntity<ApiResponse> resetPassword(String phoneNumber, ResetPassword resetPassword);

}
