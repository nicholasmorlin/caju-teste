package com.caju.helpers;

import com.caju.controllers.dto.response.AccountResponse;
import com.caju.model.Account;

public class AccountConverter {

    public static AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getDocument(),
                account.getName()
        );
    }
}
