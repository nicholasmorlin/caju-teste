package com.caju.controllers.dto.request;

public record PaymentHistoryRequestFilter (Long accountId, String category, String merchant) {
}
