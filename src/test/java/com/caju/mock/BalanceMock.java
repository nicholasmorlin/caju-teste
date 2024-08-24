package com.caju.mock;

import com.caju.model.Balance;

import java.math.BigDecimal;

import static com.caju.mock.AccountMock.generateAccount;
import static com.caju.mock.CategoryMock.generateCategory;

public class BalanceMock {

    public static Balance generateMockBalance(String categoryType, BigDecimal balance) {
        return new Balance(1L, generateAccount(), generateCategory(1L, categoryType), balance);
    }
}
