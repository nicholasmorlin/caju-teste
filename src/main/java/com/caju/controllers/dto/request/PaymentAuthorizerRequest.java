package com.caju.controllers.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentAuthorizerRequest (@NotNull Long accountId, @NotNull BigDecimal amount, String mcc, @NotBlank String merchant){}
