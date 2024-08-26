package com.caju.controllers.dto.request;

import java.math.BigDecimal;

public record PaymentAuthorizerRequest (Long accountId, BigDecimal amount, String mcc, String merchant){}
