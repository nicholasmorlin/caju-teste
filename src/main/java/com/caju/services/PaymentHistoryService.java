package com.caju.services;

import com.caju.controllers.dto.request.PaymentHistoryRequestFilter;
import com.caju.controllers.dto.response.PaymentHistoryResponse;
import com.caju.helpers.PaymentHistoryConverter;
import com.caju.model.PaymentHistory;
import com.caju.repository.PaymentHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;

    public PaymentHistoryService(PaymentHistoryRepository paymentHistoryRepository) {
        this.paymentHistoryRepository = paymentHistoryRepository;
    }

    public List<PaymentHistoryResponse> getPaymentHistoryByFilter(PaymentHistoryRequestFilter filter) {
        List<PaymentHistory> historyList = paymentHistoryRepository.findByFilter(
                filter.accountId(),
                filter.category(),
                filter.merchant()
        );

        return historyList.stream().map(PaymentHistoryConverter::toResponse).collect(Collectors.toList());
    }
}
