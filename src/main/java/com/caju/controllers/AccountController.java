package com.caju.controllers;

import com.caju.controllers.dto.request.AccountCreationRequest;
import com.caju.controllers.dto.response.AccountResponse;
import com.caju.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createAccount(@RequestBody AccountCreationRequest request) {
        accountService.createAccount(request);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long accountId) {
        AccountResponse accountResponse = accountService.getAccountById(accountId);
        return ResponseEntity.ok().body(accountResponse);
    }
}
