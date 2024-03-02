package com.example.switchwonapi.domain.payment.controller;

import com.example.switchwonapi.domain.payment.controller.request.PaymentApprovalRequest;
import com.example.switchwonapi.domain.payment.controller.request.PaymentEstimateRequest;
import com.example.switchwonapi.domain.payment.controller.response.PaymentApprovalResponse;
import com.example.switchwonapi.domain.payment.controller.response.PaymentBalanceResponse;
import com.example.switchwonapi.domain.payment.controller.response.PaymentEstimateResponse;
import com.example.switchwonapi.domain.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name = "결제 관리", description = "결제 관리 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "잔액 조회", description = "잔액 조회")
    @GetMapping("/payment/balance/{userId}")
    public ResponseEntity<PaymentBalanceResponse> getPaymentBalance(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(paymentService.getPaymentBalance(userId));
    }

    @Operation(summary = "결제 예상 결과 조회", description = "결제 예상 결과 조회")
    @PostMapping("/payment/estimate")
    public ResponseEntity<PaymentEstimateResponse> savePaymentEstimate(
            @Valid @RequestBody PaymentEstimateRequest request
    ) {
        return ResponseEntity.ok(paymentService.savePaymentEstimate(request));
    }

    @Operation(summary = "결제 승인 요청", description = "결제 승인 요청")
    @PostMapping("/payments/approval")
    public ResponseEntity<PaymentApprovalResponse> savePaymentApproval(
            @Valid @RequestBody PaymentApprovalRequest request
    ) {
        return ResponseEntity.ok(paymentService.savePaymentApproval(request));
    }
}
