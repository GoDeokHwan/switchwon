package com.example.switchwonapi.model.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentTypeEnum {
    PAYMENT("결제")
    , CHARGE("충전")
    ;
    private String description;
}
