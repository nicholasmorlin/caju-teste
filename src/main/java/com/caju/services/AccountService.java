package com.caju.services;

import com.caju.controllers.dto.request.AccountCreationRequest;
import com.caju.controllers.dto.response.AccountResponse;
import com.caju.exceptions.DuplicateKeyException;
import com.caju.exceptions.NotFoundException;
import com.caju.helpers.AccountConverter;
import com.caju.model.Account;
import com.caju.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createAccount(AccountCreationRequest request) {

        if (accountRepository.existsByDocument(request.document())) {
            throw new DuplicateKeyException("Account already registed");
        }

        Account account = new Account(request.document(), request.name());
        accountRepository.save(account);
    }

    public AccountResponse getAccountById(Long accountId) {
        return findAccountById(accountId)
                .map(AccountConverter::toResponse)
                .orElseThrow(() -> new NotFoundException("Account is not registered"));
    }

    public Optional<Account> findAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }
}
