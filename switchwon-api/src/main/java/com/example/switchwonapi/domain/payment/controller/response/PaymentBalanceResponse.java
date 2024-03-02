package com.example.switchwonapi.domain.payment.controller.response;

import com.example.switchwonapi.model.payment.enums.PaymentCurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentBalanceResponse {
    private Long userId;
    private double balance;
    private PaymentCurrencyEnum currency;
}
