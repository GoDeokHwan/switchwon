package com.example.switchwonapi.domain.payment.controller.response;

import com.example.switchwonapi.model.payment.enums.PaymentCurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentEstimateResponse {

    private double estimatedTotal;
    private double fees;
    private PaymentCurrencyEnum currency;
}
