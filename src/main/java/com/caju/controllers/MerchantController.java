package com.caju.controllers;

import com.caju.controllers.dto.request.MerchantCreationRequest;
import com.caju.controllers.dto.response.MerchantResponse;
import com.caju.services.MerchantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/merchant")
public class MerchantController {

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createMerchant(@RequestBody MerchantCreationRequest merchantCreationRequest) {
        merchantService.createMerchant(merchantCreationRequest);
    }

    @GetMapping("/{id}")
    public MerchantResponse getMerchantById(@PathVariable Long id) {
        return merchantService.getMerchant(id);
    }
}
