package com.caju.controllers.dto.request;

import java.time.LocalDateTime;

public record PaymentHistoryRequestFilter (Long accountId, String category, String merchant, LocalDateTime startDate, LocalDateTime endDate) {
}
