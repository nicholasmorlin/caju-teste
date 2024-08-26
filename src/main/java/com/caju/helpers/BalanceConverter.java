package com.caju.helpers;

import com.caju.controllers.dto.response.BalanceResponse;
import com.caju.model.Balance;

public class BalanceConverter {

    public static BalanceResponse toResponse(Balance balance) {
        return new BalanceResponse(
                balance.getAccountId().getId(),
                balance.getCategoryId().getType(),
                balance.getBalance()
        );
    }
}
