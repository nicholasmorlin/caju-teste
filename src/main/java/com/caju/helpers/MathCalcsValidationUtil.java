package com.caju.helpers;

import java.math.BigDecimal;

public class MathCalcsValidationUtil {

    public static boolean validateBalance(BigDecimal amountSpent, BigDecimal totalBalance) {
        if (amountSpent == null || totalBalance == null) {
            throw new IllegalArgumentException("amountSpent and totalBalance cannot be null");
        }
        return totalBalance.subtract(amountSpent).compareTo(BigDecimal.ZERO) >= 0;
    }
}
