package com.example.switchwonapi.model.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatusEnum {
    APPROVAL("승인")
    , CANCEL("취소")
    , WAIT("승인대기")
    , FAIL("실패")
    ;
    private String description;
}
