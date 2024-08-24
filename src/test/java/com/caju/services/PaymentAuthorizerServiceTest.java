package com.caju.services;


import com.caju.controllers.dto.request.PaymentAuthorizerRequest;
import com.caju.controllers.dto.response.PaymentAuthorizerResponse;
import com.caju.exceptions.NotFoundException;
import com.caju.mock.PaymentAuthorizerMock;
import com.caju.model.Balance;
import com.caju.model.Category;
import com.caju.model.Merchant;
import com.caju.model.PaymentHistory;
import com.caju.repository.PaymentAuthorizerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static com.caju.mock.BalanceMock.generateMockBalance;
import static com.caju.mock.CategoryMock.generateCategory;
import static com.caju.mock.PaymentAuthorizerMock.createPaymentPayload;
import static com.caju.services.MccServiceTest.generateMockMcc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentAuthorizerServiceTest {

    @InjectMocks
    private PaymentAuthorizerService service;
    @Mock
    private BalanceService balanceService;
    @Mock
    private MccService mccService;
    @Mock
    private MerchantService merchantService;
    @Mock
    private PaymentAuthorizerRepository repository;

    @Test
    void shouldReturnApprovedWhenMerchantIsFoundAndBalanceIsSufficient() {
        PaymentAuthorizerRequest paymentAuthorizerRequest = createPaymentPayload(
                1L, BigDecimal.valueOf(200), "5541", "ACOUGUE DO CLEBER");

        Category category = generateCategory(1L, "FOOD");
        Balance balance = generateMockBalance("FOOD", BigDecimal.valueOf(1000));

        when(merchantService.findCategoryByMerchantName(paymentAuthorizerRequest.merchant())).thenReturn(Optional.of(new Merchant(1L, category, "ACOUGUE DO CLEBER")));
        when(balanceService.findBalanceByAccountIdAndCategoryId(paymentAuthorizerRequest.accountId(), category.getId())).thenReturn(balance);

        ResponseEntity<PaymentAuthorizerResponse> response = service.paymentAuthorizer(paymentAuthorizerRequest);

        assertEquals("00", response.getBody().code());
        verify(balanceService).saveNewBalance(balance, paymentAuthorizerRequest.amount());
        verify(repository).save(any(PaymentHistory.class));
    }

    @Test
    void shouldReturnApprovedWhenMerchantIsNotFoundAndMccIsFoundAndBalanceIsSufficient() {
        PaymentAuthorizerRequest paymentAuthorizerRequest = createPaymentPayload(
                1L, BigDecimal.valueOf(200), "5541", "ACOUGUE DO CLEBER");

        Category category = generateCategory(1L, "FOOD");
        Balance balance = generateMockBalance("FOOD", BigDecimal.valueOf(1000));

        when(merchantService.findCategoryByMerchantName(paymentAuthorizerRequest.merchant())).thenReturn(Optional.empty());
        when(mccService.findCategoryTypeByMccCode("5541")).thenReturn(Optional.of(generateMockMcc()));
        when(balanceService.findBalanceByAccountIdAndCategoryId(paymentAuthorizerRequest.accountId(), category.getId())).thenReturn(balance);

        ResponseEntity<PaymentAuthorizerResponse> response = service.paymentAuthorizer(paymentAuthorizerRequest);

        assertEquals("00", response.getBody().code());
        verify(balanceService).saveNewBalance(balance, paymentAuthorizerRequest.amount());
        verify(repository).save(any(PaymentHistory.class));
    }

    @Test
    void shouldReturnRejectByBalanceWhenMerchantIsNotFoundAndMccIsFoundAndCashBalanceIsNotSufficient() {
        PaymentAuthorizerRequest paymentAuthorizerRequest = createPaymentPayload(
                1L, BigDecimal.valueOf(1200), "5541", "ACOUGUE DO CLEBER");

        Category category = generateCategory(1L, "FOOD");
        Balance balance = generateMockBalance("FOOD", BigDecimal.valueOf(1000));
        Balance cashbalance = generateMockBalance("CASH", BigDecimal.valueOf(1000));

        when(merchantService.findCategoryByMerchantName(paymentAuthorizerRequest.merchant())).thenReturn(Optional.empty());
        when(mccService.findCategoryTypeByMccCode("5541")).thenReturn(Optional.of(generateMockMcc()));
        when(balanceService.findBalanceByAccountIdAndCategoryId(paymentAuthorizerRequest.accountId(), category.getId())).thenReturn(balance);
        when(balanceService.findBalanceByAccountIdAndCategoryType(paymentAuthorizerRequest.accountId(), "CASH")).thenReturn(cashbalance);

        ResponseEntity<PaymentAuthorizerResponse> response = service.paymentAuthorizer(paymentAuthorizerRequest);

        assertEquals("51", response.getBody().code());
    }

    @Test
    void shouldReturnRejectByBalanceWhenMerchantIsNotFoundAndMccIsFoundAndCashBalanceIsSufficient() {
        PaymentAuthorizerRequest paymentAuthorizerRequest = createPaymentPayload(
                1L, BigDecimal.valueOf(1200), "5541", "ACOUGUE DO CLEBER");

        Category category = generateCategory(1L, "FOOD");
        Balance balance = generateMockBalance("FOOD", BigDecimal.valueOf(1000));
        Balance cashbalance = generateMockBalance("CASH", BigDecimal.valueOf(2000));

        when(merchantService.findCategoryByMerchantName(paymentAuthorizerRequest.merchant())).thenReturn(Optional.empty());
        when(mccService.findCategoryTypeByMccCode("5541")).thenReturn(Optional.of(generateMockMcc()));
        when(balanceService.findBalanceByAccountIdAndCategoryId(paymentAuthorizerRequest.accountId(), category.getId())).thenReturn(balance);
        when(balanceService.findBalanceByAccountIdAndCategoryType(paymentAuthorizerRequest.accountId(), "CASH")).thenReturn(cashbalance);

        ResponseEntity<PaymentAuthorizerResponse> response = service.paymentAuthorizer(paymentAuthorizerRequest);

        assertEquals("00", response.getBody().code());
    }

    @Test
    void shouldReturnUnavailableWhenNotFoundExceptionIsThrow() {
        PaymentAuthorizerRequest paymentAuthorizerRequest = createPaymentPayload(
                1L, BigDecimal.valueOf(1200), "5541", "ACOUGUE DO CLEBER");
        Category category = generateCategory(1L, "FOOD");

        when(merchantService.findCategoryByMerchantName(paymentAuthorizerRequest.merchant())).thenReturn(Optional.empty());
        when(mccService.findCategoryTypeByMccCode("5541")).thenReturn(Optional.of(generateMockMcc()));
        when(balanceService.findBalanceByAccountIdAndCategoryId(paymentAuthorizerRequest.accountId(), category.getId())).thenThrow(NotFoundException.class);

        ResponseEntity<PaymentAuthorizerResponse> response = service.paymentAuthorizer(paymentAuthorizerRequest);

        assertEquals("07", response.getBody().code());
    }


}
