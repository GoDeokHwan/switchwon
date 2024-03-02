package com.example.switchwonapi.domain.payment.controller.request;

import com.example.switchwonapi.global.support.valid.EnumPattern;
import com.example.switchwonapi.model.payment.enums.PaymentCurrencyEnum;
import com.example.switchwonapi.model.payment.enums.PaymentMethodEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "결제승인요청 Request")
@Getter
@Builder
public class PaymentApprovalRequest {
    @Schema(description = "유저ID", example = "4")
    @NotNull(message = "유저 정보는 필수 값입니다.")
    private Long userId;
    @Schema(description = "금액", example = "155")
    @Min(value = 1, message = "최소 1원 이상 결제 가능합니다.")
    @NotNull(message = "결제 가격은 필수 값입니다.")
    private Double amount;
    @Schema(description = "화폐타입", example = "USD")
    @EnumPattern(regexp = "USD|KRW", message = "화폐타입은 (USD,KRW)만 가능합니다.")
    private PaymentCurrencyEnum currency;
    @Schema(description = "상점정보", example = "1")
    @NotNull(message = "상점 정보는 필수 값입니다.")
    private Long merchantId;
    @Schema(description = "결제수단", example = "CREDIT_CARD")
    @EnumPattern(regexp = "CREDIT_CARD", message = "결제수단은 (CREDIT_CARD)만 가능합니다.")
    private PaymentMethodEnum paymentMethod;
    @Schema(description = "결제정보")
    @Valid
    @NotNull(message = "결제 정보는 필수 값입니다.")
    private PaymentDetails paymentDetails;
}
