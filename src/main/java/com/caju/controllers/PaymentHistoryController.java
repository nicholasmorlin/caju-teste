package com.caju.controllers;

import com.caju.controllers.dto.request.PaymentHistoryRequestFilter;
import com.caju.controllers.dto.response.PaymentHistoryResponse;
import com.caju.model.PaymentHistory;
import com.caju.services.PaymentHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/payment-history")
public class PaymentHistoryController {

    private final PaymentHistoryService paymentHistoryService;

    public PaymentHistoryController(PaymentHistoryService paymentHistoryService) {
        this.paymentHistoryService = paymentHistoryService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentHistoryResponse>> getPaymentHistory(@RequestBody PaymentHistoryRequestFilter filter) {
        List<PaymentHistoryResponse> history = paymentHistoryService.getPaymentHistoryByFilter(filter);
        return ResponseEntity.ok(history);
    }
}
