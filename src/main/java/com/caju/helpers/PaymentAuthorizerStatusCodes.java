package com.caju.helpers;

public enum PaymentAuthorizerStatusCodes {

    APPROVED("00"),
    REJECTED_BY_BALANCE("51"),
    UNAVALIABLE("07");

    private final String code;

    PaymentAuthorizerStatusCodes(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
