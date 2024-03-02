package com.example.switchwonapi.domain.payment.controller.request;

import com.example.switchwonapi.global.support.valid.EnumPattern;
import com.example.switchwonapi.model.payment.enums.PaymentCurrencyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "결제 예상금액 요청 Request")
@Getter
@Builder
public class PaymentEstimateRequest {
    @Schema(description = "요청금액", example = "150")
    @NotNull(message = "요청금액은 필수 값입니다.")
    private Double amount;
    @Schema(description = "화폐타입", example = "USD")
    @EnumPattern(regexp = "USD|KRW", message = "화폐타입은 (USD,KRW)만 가능합니다.")
    private PaymentCurrencyEnum currency;
    @Schema(description = "가게 정보", example = "1")
    @NotNull(message = "요청 가게 정보는 필수 값입니다.")
    private Long destination;
    @Schema(description = "요청자ID", example = "1")
    @NotNull(message = "요청자 정보는 필수 값입니다.")
    private Long userId;
}
