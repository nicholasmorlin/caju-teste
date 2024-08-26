package com.caju.services;

import com.caju.controllers.dto.request.BalanceAddFundsRequest;
import com.caju.controllers.dto.response.BalanceResponse;
import com.caju.exceptions.NotFoundException;
import com.caju.helpers.BalanceConverter;
import com.caju.model.Account;
import com.caju.model.Balance;
import com.caju.model.Category;
import com.caju.repository.BalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BalanceService {

    private static final Logger logger = LoggerFactory.getLogger(BalanceService.class);

    private final AccountService accountService;
    private final CategoryService categoryService;
    private final BalanceRepository balanceRepository;

    public BalanceService(AccountService accountService, CategoryService categoryService, BalanceRepository balanceRepository) {
        this.accountService = accountService;
        this.categoryService = categoryService;
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

    public Optional<Balance> findOrCreateBalanceByAccountIdAndCategoryType(Long accountId, String type) {
        return balanceRepository.findBalanceByAccountIdAndCategoryType(accountId, type);
    }

    public void saveNewBalance(Balance balance, BigDecimal totalSpent) {
        balance.setBalance(balance.getBalance().subtract(totalSpent));
        saveBalance(balance);
    }

    private void saveBalance(Balance balance) {
        balanceRepository.save(balance);
    }

    @Transactional
    public void addFundsToBalance(BalanceAddFundsRequest balanceAddFundsRequest) {
        Account account = accountService.findAccountById(balanceAddFundsRequest.accountId())
                .orElseThrow(() -> new NotFoundException("Account is not registered"));

        Category category = categoryService.findCategoryByType(balanceAddFundsRequest.categoryName())
                .orElseThrow(() -> new NotFoundException("Category is not registered"));

        Optional<Balance> balanceOptional = findOrCreateBalanceByAccountIdAndCategoryType(
                balanceAddFundsRequest.accountId(),
                balanceAddFundsRequest.categoryName());

        if (balanceOptional.isPresent()) {
            Balance balance = balanceOptional.get();
            balance.setBalance(balance.getBalance().add(balanceAddFundsRequest.amount()));
            saveBalance(balance);
        } else if (balanceOptional.isEmpty()) {
            Balance balance = new Balance(account, category, balanceAddFundsRequest.amount());
            saveBalance(balance);
        }
    }

    public List<BalanceResponse> findAllBalancesForAccountId(Long accountId) {
        return balanceRepository.findAllByAccountId(accountId)
                .stream().map(BalanceConverter::toResponse)
                .collect(Collectors.toList());
    }
}
