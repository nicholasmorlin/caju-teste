package com.caju.services;

import com.caju.controllers.dto.request.PaymentAuthorizerRequest;
import com.caju.controllers.dto.response.PaymentAuthorizerResponse;
import com.caju.helpers.PaymentAuthorizerStatusCodes;
import com.caju.model.Balance;
import com.caju.model.Mcc;
import com.caju.model.PaymentHistory;
import com.caju.repository.PaymentAuthorizerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static com.caju.helpers.PaymentAuthorizerStatusCodes.*;

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

        final Optional<Mcc> mccOptional = mccService.findCategoryTypeByMccCode(paymentAuthorizerRequest.mcc());

        if (mccOptional.isPresent()) {
            Balance balance = balanceService.findBalanceByAccountIdAndCategoryId(paymentAuthorizerRequest.accountId(), mccOptional.get().getCategoryId().getId());

            if (!validateBalance(paymentAuthorizerRequest.amount(), balance.getBalance())) {
                return mapToPaymentAuthorizerResponse(REJECTED_BY_BALANCE);
            }

            balanceService.saveNewBalance(balance, paymentAuthorizerRequest.amount());
            mapAndPersistPaymentHistory(paymentAuthorizerRequest, balance, mccOptional.get());
            return mapToPaymentAuthorizerResponse(APPROVED);
        }

        return mapToPaymentAuthorizerResponse(UNAVALIABLE);
    }

    private boolean validateBalance(BigDecimal amountSpent, BigDecimal totalBalance) {
        if (amountSpent == null || totalBalance == null) {
            throw new IllegalArgumentException("amountSpent and totalBalance cannot be null");
        }
        return totalBalance.subtract(amountSpent).compareTo(BigDecimal.ZERO) >= 0;
    }

    private ResponseEntity<PaymentAuthorizerResponse> mapToPaymentAuthorizerResponse(PaymentAuthorizerStatusCodes statusCodes){
        PaymentAuthorizerResponse response = new PaymentAuthorizerResponse(statusCodes.getCode());
        return ResponseEntity.ok().body(response);
    }

    private void mapAndPersistPaymentHistory(PaymentAuthorizerRequest paymentAuthorizerRequest, Balance balance, Mcc mcc) {
        PaymentHistory paymentHistory = new PaymentHistory(balance.getAccountId(), mcc, paymentAuthorizerRequest.amount(), paymentAuthorizerRequest.merchant());
        paymentAuthorizerRepository.save(paymentHistory);
    }
}
