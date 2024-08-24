package com.caju.services;

import com.caju.controllers.dto.request.PaymentAuthorizerRequest;
import com.caju.controllers.dto.response.PaymentAuthorizerResponse;
import com.caju.helpers.PaymentAuthorizerStatusCodes;
import com.caju.model.*;
import com.caju.model.enums.CategoryType;
import com.caju.repository.PaymentAuthorizerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.caju.helpers.PaymentAuthorizerStatusCodes.*;
import static com.caju.util.MathCalcsValidationUtil.validateBalance;

@Service
public class PaymentAuthorizerService {
    private final MccService mccService;
    private final BalanceService balanceService;
    private final MerchantService merchantService;
    private final PaymentAuthorizerRepository paymentAuthorizerRepository;

    public PaymentAuthorizerService(MccService mccService, BalanceService balanceService, MerchantService merchantService, PaymentAuthorizerRepository paymentAuthorizerRepository) {
        this.mccService = mccService;
        this.balanceService = balanceService;
        this.merchantService = merchantService;
        this.paymentAuthorizerRepository = paymentAuthorizerRepository;
    }

    public ResponseEntity<PaymentAuthorizerResponse> paymentAuthorizer(PaymentAuthorizerRequest paymentAuthorizerRequest) {

        try {
            final Optional<Merchant> merchantOptional = merchantService.findCategoryByMerchantName(paymentAuthorizerRequest.merchant());
            Optional<Mcc> mccOptional = Optional.empty();

            if (merchantOptional.isPresent()) {
               return mapToPaymentAuthorizerResponse(authorizeTransaction(paymentAuthorizerRequest, mccOptional, merchantOptional.get().getCategory()));
            }

            mccOptional = mccService.findCategoryTypeByMccCode(paymentAuthorizerRequest.mcc());
            return mapToPaymentAuthorizerResponse(authorizeTransaction(paymentAuthorizerRequest, mccOptional, null));
        } catch (Exception e) {
            return mapToPaymentAuthorizerResponse(UNAVALIABLE);
        }
    }

    private PaymentAuthorizerStatusCodes authorizeTransaction(PaymentAuthorizerRequest paymentAuthorizerRequest, Optional<Mcc> mccOptional, Category category) {

        if (mccOptional.isPresent() || Objects.nonNull(category)) {
            PaymentAuthorizerStatusCodes status = handleSimpleAuthorizer(paymentAuthorizerRequest, mccOptional, category);
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

    private PaymentAuthorizerStatusCodes handleSimpleAuthorizer(PaymentAuthorizerRequest paymentAuthorizerRequest, Optional<Mcc> mccOptional, Category category) {

        Balance balance;

        if (mccOptional.isPresent()) {
            balance = balanceService.findBalanceByAccountIdAndCategoryId(paymentAuthorizerRequest.accountId(), mccOptional.get().getCategoryId().getId());
            return processAuthorization(paymentAuthorizerRequest, balance, mccOptional.get(), mccOptional.get().getCategoryId());

        } else if (mccOptional.isEmpty()) {
            balance = balanceService.findBalanceByAccountIdAndCategoryId(paymentAuthorizerRequest.accountId(), category.getId());
            return processAuthorization(paymentAuthorizerRequest, balance, null, category);
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

    private PaymentAuthorizerStatusCodes processAuthorization(PaymentAuthorizerRequest paymentAuthorizerRequest, Balance balance, Mcc mcc, Category category) {
        if (validateBalance(paymentAuthorizerRequest.amount(), balance.getBalance())) {
            balanceService.saveNewBalance(balance, paymentAuthorizerRequest.amount());
            mapAndPersistPaymentHistory(paymentAuthorizerRequest, balance, mcc, category);
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
