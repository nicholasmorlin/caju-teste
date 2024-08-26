package com.caju.controllers.dto.response;

import java.math.BigDecimal;

public record BalanceResponse (Long accountId, String categoryType, BigDecimal balance) {}
