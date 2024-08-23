package com.caju.services;

import com.caju.model.Balance;
import com.caju.repository.BalanceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceService {

    private final BalanceRepository balanceRepository;

    private final ModelMapper modelMapper;

    public BalanceService(BalanceRepository balanceRepository, ModelMapper modelMapper) {
        this.balanceRepository = balanceRepository;
        this.modelMapper = modelMapper;
    }

    public Balance findBalanceByAccountIdAndCategoryId(Long accountId, Long categoryId) {
        return balanceRepository.findBalanceByAccountIdAndCategoryId(accountId, categoryId);
    }

    public void saveNewBalance(Balance balance, BigDecimal totalSpent) {
        balance.setBalance(balance.getBalance().subtract(totalSpent));
        saveBalance(balance);
    }

    public void saveBalance(Balance balance) {
        balanceRepository.save(balance);
    }
}
