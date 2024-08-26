package com.caju.controllers;

import com.caju.controllers.dto.request.PaymentAuthorizerRequest;
import com.caju.controllers.dto.response.PaymentAuthorizerResponse;
import com.caju.services.PaymentAuthorizerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payment-authorizer")
public class PaymentAuthorizerController {

    private final PaymentAuthorizerService service;

    public PaymentAuthorizerController(PaymentAuthorizerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PaymentAuthorizerResponse> authorizer(@RequestBody PaymentAuthorizerRequest paymentAuthorizerRequest) {
        return service.paymentAuthorizer(paymentAuthorizerRequest);
    }
}
