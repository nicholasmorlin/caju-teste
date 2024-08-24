package com.caju.services;

import com.caju.exceptions.NotFoundException;
import com.caju.model.Balance;
import com.caju.repository.BalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BalanceService {

    private static final Logger logger = LoggerFactory.getLogger(BalanceService.class);

    private final BalanceRepository balanceRepository;

    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Transactional
    public Balance findBalanceByAccountIdAndCategoryId(Long accountId, Long categoryId) {

        return balanceRepository.findBalanceByAccountIdAndCategoryId(accountId, categoryId)
                .orElseThrow(() -> {
                    logger.error("Balance not found for accountId: {} e categoryId: {}", accountId, categoryId);
                    return new NotFoundException("Balance not found");
                });
    }

    @Transactional
    public Balance findBalanceByAccountIdAndCategoryType(Long accountId, String type) {
        return balanceRepository.findBalanceByAccountIdAndCategoryType(accountId, type)
                .orElseThrow(() -> {
                    logger.error("Balance not found for accountId: {} e type: {}", accountId, type);
                    return new NotFoundException("Balance not found");
                });
    }

    public void saveNewBalance(Balance balance, BigDecimal totalSpent) {
        balance.setBalance(balance.getBalance().subtract(totalSpent));
        saveBalance(balance);
    }

    public void saveBalance(Balance balance) {
        balanceRepository.save(balance);
    }
}
