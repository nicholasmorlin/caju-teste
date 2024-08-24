package com.caju.services;

import com.caju.controllers.dto.request.PaymentAuthorizerRequest;
import com.caju.controllers.dto.response.PaymentAuthorizerResponse;
import com.caju.helpers.PaymentAuthorizerStatusCodes;
import com.caju.model.Balance;
import com.caju.model.Category;
import com.caju.model.Mcc;
import com.caju.model.PaymentHistory;
import com.caju.model.enums.CategoryType;
import com.caju.repository.PaymentAuthorizerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.caju.helpers.PaymentAuthorizerStatusCodes.*;
import static com.caju.util.MathCalcsValidationUtil.validateBalance;

@Service
public class PaymentAuthorizerService {
    private final MccService mccService;
    private final BalanceService balanceService;
    private final PaymentAuthorizerRepository paymentAuthorizerRepository;

    public PaymentAuthorizerService(MccService mccService, BalanceService balanceService, PaymentAuthorizerRepository paymentAuthorizerRepository) {
        this.mccService = mccService;
        this.balanceService = balanceService;
        this.paymentAuthorizerRepository = paymentAuthorizerRepository;
    }

    public ResponseEntity<PaymentAuthorizerResponse> paymentAuthorizer(PaymentAuthorizerRequest paymentAuthorizerRequest) {

        try {
            final Optional<Mcc> mccOptional = mccService.findCategoryTypeByMccCode(paymentAuthorizerRequest.mcc());
            PaymentAuthorizerStatusCodes status = authorizeTransaction(paymentAuthorizerRequest, mccOptional);
            return mapToPaymentAuthorizerResponse(status);
        } catch (Exception e) {
            return mapToPaymentAuthorizerResponse(UNAVALIABLE);
        }
    }

    private PaymentAuthorizerStatusCodes authorizeTransaction(PaymentAuthorizerRequest paymentAuthorizerRequest, Optional<Mcc> mccOptional) {

        if (mccOptional.isPresent()) {
            PaymentAuthorizerStatusCodes status = handleSimpleAuthorizer(paymentAuthorizerRequest, mccOptional);
            if (status == APPROVED) {
                return APPROVED;
            }
        }

        PaymentAuthorizerStatusCodes fallbackStatus = handleFallbackAuthorizer(paymentAuthorizerRequest);
        if (fallbackStatus == APPROVED) {
            return APPROVED;
        }

        return REJECTED_BY_BALANCE;
    }

    private PaymentAuthorizerStatusCodes handleSimpleAuthorizer(PaymentAuthorizerRequest paymentAuthorizerRequest, Optional<Mcc> mccOptional) {
        Balance balance = balanceService.findBalanceByAccountIdAndCategoryId(paymentAuthorizerRequest.accountId(), mccOptional.get().getCategoryId().getId());

        if (validateBalance(paymentAuthorizerRequest.amount(), balance.getBalance())) {
            balanceService.saveNewBalance(balance, paymentAuthorizerRequest.amount());
            mapAndPersistPaymentHistory(paymentAuthorizerRequest, balance, mccOptional.get(), mccOptional.get().getCategoryId());
            return APPROVED;
        }

        return REJECTED_BY_BALANCE;
    }

    private PaymentAuthorizerStatusCodes handleFallbackAuthorizer(PaymentAuthorizerRequest paymentAuthorizerRequest) {
        Balance cashBalance = balanceService.findBalanceByAccountIdAndCategoryType(paymentAuthorizerRequest.accountId(), CategoryType.CASH.name());

        if (validateBalance(paymentAuthorizerRequest.amount(), cashBalance.getBalance())) {
            balanceService.saveNewBalance(cashBalance, paymentAuthorizerRequest.amount());
            mapAndPersistPaymentHistory(paymentAuthorizerRequest, cashBalance, null, cashBalance.getCategoryId());
            return APPROVED;
        }

        return REJECTED_BY_BALANCE;
    }

    private ResponseEntity<PaymentAuthorizerResponse> mapToPaymentAuthorizerResponse(PaymentAuthorizerStatusCodes statusCodes) {
        PaymentAuthorizerResponse response = new PaymentAuthorizerResponse(statusCodes.getCode());
        return ResponseEntity.ok().body(response);
    }

    private void mapAndPersistPaymentHistory(PaymentAuthorizerRequest paymentAuthorizerRequest, Balance balance, Mcc mcc, Category category) {
        PaymentHistory paymentHistory = new PaymentHistory(
                balance.getAccountId(),
                mcc,
                paymentAuthorizerRequest.amount(),
                paymentAuthorizerRequest.merchant(),
                category);
        paymentAuthorizerRepository.save(paymentHistory);
    }
}
