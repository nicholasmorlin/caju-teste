package com.caju.mock;

import com.caju.model.Account;

public class AccountMock {

    public static Account generateAccount() {
        return new Account(1L, "21313131", "pessoa");
    }
}
