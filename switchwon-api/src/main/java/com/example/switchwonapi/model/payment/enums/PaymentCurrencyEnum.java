package com.example.switchwonapi.model.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentCurrencyEnum {
    USD("달러")
    , KRW("원화")
    ;

    private String description;
}
