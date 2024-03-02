package com.example.switchwonapi.domain.payment.controller.request;

import com.example.switchwonapi.global.support.valid.CardNumber;
import com.example.switchwonapi.global.support.valid.ExpiryDate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "카드 정보")
@Getter
@Builder
public class PaymentDetails {
    @Schema(description = "카드번호", example = "1234-1234-1234-1234")
    @CardNumber
    private String cardNumber;
    @Schema(description = "카드만료일", example = "12/25")
    @ExpiryDate
    private String expiryDate;
    @Schema(description = "CVV", example = "125")
    @Min(value = 100)
    @Max(value = 999)
    @NotNull(message = "CVV는 필수값입니다.")
    private Integer cvv;
}
