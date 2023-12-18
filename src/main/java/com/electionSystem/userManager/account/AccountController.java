package com.electionSystem.userManager.account;

import com.electionSystem.userManager.account.dto.ChangePassword;
import com.electionSystem.userManager.account.dto.ResetPassword;
import com.electionSystem.utils.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Account API.")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping({"/change-password"})
    public ResponseEntity<ApiResponse> changePassword(@RequestBody @Valid ChangePassword changePassword) {
        return accountService.changePassword(changePassword);
    }

    @PutMapping({"/reset-password/{username}"})
    public ResponseEntity<ApiResponse> changePassword(@PathVariable String username, @RequestBody @Valid ResetPassword resetPassword) {
        return accountService.resetPassword(username, resetPassword);
    }

}


