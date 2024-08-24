package com.caju.mock;

import com.caju.controllers.dto.request.PaymentAuthorizerRequest;

import java.math.BigDecimal;

public class PaymentAuthorizerMock {

    public static PaymentAuthorizerRequest createPaymentPayload(Long accountId, BigDecimal amount, String mcc, String merchant) {
        return new PaymentAuthorizerRequest(accountId, amount, mcc, merchant);
    }
}
