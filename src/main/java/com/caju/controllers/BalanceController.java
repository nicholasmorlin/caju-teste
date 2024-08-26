package com.caju.controllers;

import com.caju.controllers.dto.request.BalanceAddFundsRequest;
import com.caju.controllers.dto.response.BalanceResponse;
import com.caju.services.BalanceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/balance")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PatchMapping
    public void addFundsToAccountBalance(@RequestBody @Validated BalanceAddFundsRequest balanceAddFundsRequest) {
        balanceService.addFundsToBalance(balanceAddFundsRequest);
    }

    @GetMapping("/{accountId}")
    public List<BalanceResponse> findAllBalancesForAccountId(@PathVariable Long accountId) {
        return balanceService.findAllBalancesForAccountId(accountId);
    }
}
