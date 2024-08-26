package com.caju.controllers.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record BalanceAddFundsRequest (@NotNull Long accountId, @NotBlank String categoryName, @NotNull @Positive BigDecimal amount) {}
