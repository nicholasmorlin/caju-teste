package com.caju.helpers;

import com.caju.controllers.dto.response.MerchantResponse;
import com.caju.model.Merchant;

public class MerchantConverter {

    public static MerchantResponse toResponse(Merchant merchant) {
        return new MerchantResponse(
                merchant.getId(),
                merchant.getCategory().getType(),
                merchant.getName()
        );
    }
}
