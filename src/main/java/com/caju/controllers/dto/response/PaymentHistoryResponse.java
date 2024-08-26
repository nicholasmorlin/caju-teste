package com.caju.controllers.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentHistoryResponse(Long id, Long accountId, String categoryType, BigDecimal amount, String merchant, LocalDateTime creationDate){}