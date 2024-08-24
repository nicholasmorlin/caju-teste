package com.caju.services;

import com.caju.model.Balance;
import com.caju.repository.BalanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BalanceService {

    private final BalanceRepository balanceRepository;

    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Transactional
    public Balance findBalanceByAccountIdAndCategoryId(Long accountId, Long categoryId) {
        return balanceRepository.findBalanceByAccountIdAndCategoryId(accountId, categoryId);
    }

    @Transactional
    public Balance findBalanceByAccountIdAndCategoryType(Long accountId, String type) {
        return balanceRepository.findBalanceByAccountIdAndCategoryType(accountId, type);
    }

    public void saveNewBalance(Balance balance, BigDecimal totalSpent) {
        balance.setBalance(balance.getBalance().subtract(totalSpent));
        saveBalance(balance);
    }

    public void saveBalance(Balance balance) {
        balanceRepository.save(balance);
    }
}
