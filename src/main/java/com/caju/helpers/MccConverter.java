package com.caju.helpers;

import com.caju.controllers.dto.response.MccResponse;
import com.caju.model.Mcc;

public class MccConverter {

    public static MccResponse toResponse(Mcc mcc) {
        return new MccResponse(
                mcc.getId(),
                mcc.getCategoryId().getType(),
                mcc.getCode()
        );
    }
}
