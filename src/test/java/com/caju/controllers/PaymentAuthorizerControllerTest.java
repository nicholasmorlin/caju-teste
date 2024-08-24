package com.caju.controllers;

import com.caju.controllers.dto.request.PaymentAuthorizerRequest;
import com.caju.controllers.dto.response.PaymentAuthorizerResponse;
import com.caju.helpers.PaymentAuthorizerStatusCodes;
import com.caju.services.PaymentAuthorizerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static com.caju.mock.PaymentAuthorizerMock.createPaymentPayload;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentAuthorizerController.class)
class PaymentAuthorizerControllerTest {

    private static final String V1_PAYMENT_AUTHORIZER = "/v1/payment-authorizer";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentAuthorizerService service;

    @Test
    void shouldPaymentAuthorizerReturnApproved() throws Exception {

        final PaymentAuthorizerRequest paymentAuthorizerRequest = createPaymentPayload(1L, BigDecimal.valueOf(200.00), "5541", "PADARIA DO CAJUEIRO");
        final ResponseEntity<PaymentAuthorizerResponse> paymentAuthorizerResponse = generateResponseByStatus(PaymentAuthorizerStatusCodes.APPROVED);

        final var paymentBody = """
                {
                    "accountId": 1,
                    "amount": "200.0",
                    "mcc": "5541",
                    "merchant": "PADARIA DO CAJUEIRO"
                }
                """;

        when(service.paymentAuthorizer(paymentAuthorizerRequest)).thenReturn(paymentAuthorizerResponse);

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post(V1_PAYMENT_AUTHORIZER)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(paymentBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("00"));
    }

    @Test
    void shouldPaymentAuthorizerReturnRejectByBalance() throws Exception {

        final PaymentAuthorizerRequest paymentAuthorizerRequest = createPaymentPayload(2L, BigDecimal.valueOf(1000000.0), "5541", "PADARIA DO JOAO");
        final ResponseEntity<PaymentAuthorizerResponse> paymentAuthorizerResponse = generateResponseByStatus(PaymentAuthorizerStatusCodes.REJECTED_BY_BALANCE);

        final var paymentBody = """
                {
                    "accountId": 2,
                    "amount": "1000000.0",
                    "mcc": "5541",
                    "merchant": "PADARIA DO JOAO"
                }
                """;

        when(service.paymentAuthorizer(paymentAuthorizerRequest)).thenReturn(paymentAuthorizerResponse);

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post(V1_PAYMENT_AUTHORIZER)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(paymentBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("51"));
    }

    @Test
    void shouldPaymentAuthorizerReturnUnavailable() throws Exception {

        final PaymentAuthorizerRequest paymentAuthorizerRequest = createPaymentPayload(3L, BigDecimal.valueOf(100.0), "5541", "PADARIA DO JOAQUIM");
        final ResponseEntity<PaymentAuthorizerResponse> paymentAuthorizerResponse = generateResponseByStatus(PaymentAuthorizerStatusCodes.UNAVALIABLE);

        final var paymentBody = """
                {
                    "accountId": 3,
                    "amount": "100.0",
                    "mcc": "5541",
                    "merchant": "PADARIA DO JOAQUIM"
                }
                """;

        when(service.paymentAuthorizer(paymentAuthorizerRequest)).thenReturn(paymentAuthorizerResponse);

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post(V1_PAYMENT_AUTHORIZER)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(paymentBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("07"));
    }

    @Test
    void shouldReturnErrorIfAccountIdIsMissing() throws Exception {

        final var paymentBody = """
                {
                    "accountId": ,
                    "amount": "100.0",
                    "mcc": "5541",
                    "merchant": "PADARIA DO JOAQUIM"
                }
                """;

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post(V1_PAYMENT_AUTHORIZER)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(paymentBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnErrorIfAmountIsMissing() throws Exception {

        final var paymentBody = """
                {
                    "accountId": 1,
                    "amount": ,
                    "mcc": "5541",
                    "merchant": "PADARIA DO JOAQUIM"
                }
                """;

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post(V1_PAYMENT_AUTHORIZER)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(paymentBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnErrorIfMerchantIsMissing() throws Exception {

        final var paymentBody = """
                {
                    "accountId": 1,
                    "amount": "100.0",
                    "mcc": "5541",
                    "merchant": 
                }
                """;

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post(V1_PAYMENT_AUTHORIZER)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(paymentBody))
                .andExpect(status().isBadRequest());
    }

    private ResponseEntity<PaymentAuthorizerResponse> generateResponseByStatus(PaymentAuthorizerStatusCodes status) {
        return ResponseEntity.ok().body(new PaymentAuthorizerResponse(status.getCode()));
    }
}
