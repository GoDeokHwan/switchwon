package com.example.switchwonapi.model.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethodEnum {
    CREDIT_CARD("신용카드")
    ;

    private String description;
}
