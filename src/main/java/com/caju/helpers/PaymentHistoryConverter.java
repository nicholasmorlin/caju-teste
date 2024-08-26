package com.caju.helpers;

import com.caju.controllers.dto.response.PaymentHistoryResponse;
import com.caju.model.PaymentHistory;

public class PaymentHistoryConverter {

    public static PaymentHistoryResponse toResponse(PaymentHistory paymentHistory) {
        return new PaymentHistoryResponse(
                paymentHistory.getId(),
                paymentHistory.getAccountId().getId(),
                paymentHistory.getCategoryId().getType(),
                paymentHistory.getAmount(),
                paymentHistory.getMerchant(),
                paymentHistory.getCreationDate()
        );
    }
}
