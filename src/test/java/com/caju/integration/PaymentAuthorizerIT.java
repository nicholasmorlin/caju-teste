package com.caju.integration;

import com.caju.controllers.dto.request.PaymentAuthorizerRequest;
import com.caju.controllers.dto.response.PaymentAuthorizerResponse;
import com.caju.services.PaymentAuthorizerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/schema.sql", "/data.sql"})
public class PaymentAuthorizerIT {

    @Autowired
    private PaymentAuthorizerService paymentAuthorizerService;

    @Test
    void shouldPaymentAuthorizerReturnApprovedWithInvalidMcc() {
        PaymentAuthorizerRequest paymentAuthorizerRequest = new PaymentAuthorizerRequest(
                1L,
                BigDecimal.valueOf(200.00),
                "9999",
                "PADARIA DO ZE               SAO PAULO BR");

        ResponseEntity<PaymentAuthorizerResponse> response = paymentAuthorizerService.paymentAuthorizer(paymentAuthorizerRequest);

        assertEquals(response.getBody().code(), "00");
    }

    @Test
    void shouldPaymentAuthorizerReturnApprovedWithValidMcc() {
        PaymentAuthorizerRequest paymentAuthorizerRequest = new PaymentAuthorizerRequest(
                1L,
                BigDecimal.valueOf(200.00),
                "5811",
                "PADARIA DO ZE               SAO PAULO BR");

        ResponseEntity<PaymentAuthorizerResponse> response = paymentAuthorizerService.paymentAuthorizer(paymentAuthorizerRequest);

        assertEquals(response.getBody().code(), "00");
    }

    @Test
    void shouldPaymentAuthorizerReturnRejectByBalanceWithInvalidMcc() {
        PaymentAuthorizerRequest paymentAuthorizerRequest = new PaymentAuthorizerRequest(
                1L,
                BigDecimal.valueOf(4000.00),
                "9999",
                "PADARIA DO ZE               SAO PAULO BR");

        ResponseEntity<PaymentAuthorizerResponse> response = paymentAuthorizerService.paymentAuthorizer(paymentAuthorizerRequest);

        assertEquals(response.getBody().code(), "51");
    }

    @Test
    void shouldPaymentAuthorizerReturnRejectByBalanceWithValidMcc() {
        PaymentAuthorizerRequest paymentAuthorizerRequest = new PaymentAuthorizerRequest(
                1L,
                BigDecimal.valueOf(4000.00),
                "5411",
                "PADARIA DO ZE               SAO PAULO BR");

        ResponseEntity<PaymentAuthorizerResponse> response = paymentAuthorizerService.paymentAuthorizer(paymentAuthorizerRequest);

        assertEquals(response.getBody().code(), "51");
    }

    @Test
    void shouldPaymentAuthorizerReturnUnavailableWithInvalidPayload() {
        PaymentAuthorizerRequest paymentAuthorizerRequest = new PaymentAuthorizerRequest(
                null,
                BigDecimal.valueOf(4000.00),
                "5411",
                "");

        ResponseEntity<PaymentAuthorizerResponse> response = paymentAuthorizerService.paymentAuthorizer(paymentAuthorizerRequest);

        assertEquals(response.getBody().code(), "07");
    }
}
