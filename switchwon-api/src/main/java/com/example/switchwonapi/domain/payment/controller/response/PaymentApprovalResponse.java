package com.example.switchwonapi.domain.payment.controller.response;

import com.example.switchwonapi.model.payment.enums.PaymentCurrencyEnum;
import com.example.switchwonapi.model.payment.enums.PaymentStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "결제 승인 정보")
@Getter
@AllArgsConstructor
public class PaymentApprovalResponse {
    @Schema(description = "결제ID", example = "1")
    private Long paymentId;
    @Schema(description = "결제 상태", example = "APPROVAL")
    private PaymentStatusEnum status;
    @Schema(description = "결제 금액", example = "155")
    private double amount;
    @Schema(description = "화폐타입", example = "USD")
    private PaymentCurrencyEnum currency;
    @Schema(description = "요청시간")
    private LocalDateTime timestamp;
}
