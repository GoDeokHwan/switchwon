package com.example.switchwonapi.domain.payment.service;

import com.example.switchwonapi.domain.payment.controller.request.PaymentApprovalRequest;
import com.example.switchwonapi.domain.payment.controller.request.PaymentDetails;
import com.example.switchwonapi.domain.payment.controller.request.PaymentEstimateRequest;
import com.example.switchwonapi.domain.payment.controller.response.PaymentApprovalResponse;
import com.example.switchwonapi.domain.payment.controller.response.PaymentBalanceResponse;
import com.example.switchwonapi.domain.payment.controller.response.PaymentEstimateResponse;
import com.example.switchwonapi.model.payment.enums.PaymentCurrencyEnum;
import com.example.switchwonapi.model.payment.enums.PaymentMethodEnum;
import com.example.switchwonapi.model.payment.enums.PaymentStatusEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.webjars.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceTest {
    @Autowired
    private PaymentService paymentService;

//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }

    @Test
    void 잔액_조회() {
        // given
        Long userId = 1L;
        // when
        PaymentBalanceResponse response = paymentService.getPaymentBalance(userId);
        // then
        assertEquals(response.getUserId(), userId);
        assertEquals(response.getBalance(), 1000);
        assertEquals(response.getCurrency(), PaymentCurrencyEnum.USD);
    }

    @Test
    void 잔액_조회_대상에_포인트_정보없이도_나오는지_확인() {
        // given
        Long userId = 2L;
        // when
        PaymentBalanceResponse response = paymentService.getPaymentBalance(userId);
        // then
        assertEquals(response.getUserId(), userId);
        assertEquals(response.getBalance(), 0);
        assertNull(response.getCurrency());
    }

    @Test
    void 잔액_조회_대상_못_찾음() {
        // given
        Long userId = 999L;
        // when
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            PaymentBalanceResponse response = paymentService.getPaymentBalance(userId);
        });
        // then
        assertEquals(exception.getMessage(), "사용자를 찾을 수 없습니다.");
    }

    @Test
    void 예상_잔액_조회_대상_못_찾음() {
        // given
        PaymentEstimateRequest request = PaymentEstimateRequest.builder()
                .userId(999L)
                .destination(1L)
                .amount(155.0)
                .currency(PaymentCurrencyEnum.USD)
                .build();
        // when
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            PaymentEstimateResponse response = paymentService.savePaymentEstimate(request);
        });
        // then
        assertEquals(exception.getMessage(), "사용자를 찾을 수 없습니다.");
    }


    @Test
    void 예상_잔액_조회_마켓_못_찾음() {
        // given
        PaymentEstimateRequest request = PaymentEstimateRequest.builder()
                .userId(1L)
                .destination(999L)
                .amount(155.0)
                .currency(PaymentCurrencyEnum.USD)
                .build();
        // when
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            PaymentEstimateResponse response = paymentService.savePaymentEstimate(request);
        });
        // then
        assertEquals(exception.getMessage(), "상점을 찾을 수 없습니다.");
    }

    @Test
    void 예상_잔액_조회() {
        // given
        PaymentEstimateRequest request = PaymentEstimateRequest.builder()
                .userId(2L)
                .destination(1L)
                .amount(150.0)
                .currency(PaymentCurrencyEnum.USD)
                .build();
        // when
        PaymentEstimateResponse response = paymentService.savePaymentEstimate(request);

        // then
        assertEquals(response.getEstimatedTotal(), 155);
        assertEquals(response.getFees(), 5);
        assertEquals(response.getCurrency(), PaymentCurrencyEnum.USD);
    }

    @Test
    void 결제_승인_요청() {
        // given
        PaymentApprovalRequest request = PaymentApprovalRequest.builder()
                .userId(3L)
                .amount(200.0)
                .currency(PaymentCurrencyEnum.USD)
                .merchantId(1L)
                .paymentMethod(PaymentMethodEnum.CREDIT_CARD)
                .paymentDetails(
                        PaymentDetails.builder()
                                .cardNumber("1234-5678-9123-4567")
                                .expiryDate("12/24")
                                .cvv(123)
                                .build()
                )
                .build();
        // when
        PaymentApprovalResponse response = paymentService.savePaymentApproval(request);

        // then
        assertEquals(response.getStatus(), PaymentStatusEnum.APPROVAL);
        assertEquals(response.getAmount(), 200.0);
        assertEquals(response.getCurrency(), PaymentCurrencyEnum.USD);
    }

    @Test
    void 결제_승인_요청_계좌에_돈이없는_경우() {
        // given
        PaymentApprovalRequest request = PaymentApprovalRequest.builder()
                .userId(5L)
                .amount(200.0)
                .currency(PaymentCurrencyEnum.USD)
                .merchantId(1L)
                .paymentMethod(PaymentMethodEnum.CREDIT_CARD)
                .paymentDetails(
                        PaymentDetails.builder()
                                .cardNumber("1234-5678-9123-4567")
                                .expiryDate("12/24")
                                .cvv(123)
                                .build()
                )
                .build();
        // when
        PaymentApprovalResponse response = paymentService.savePaymentApproval(request);

        // then
        assertEquals(response.getStatus(), PaymentStatusEnum.APPROVAL);
        assertEquals(response.getAmount(), 200.0);
        assertEquals(response.getCurrency(), PaymentCurrencyEnum.USD);
    }

    @Test
    void 결제_승인_요청_없는카드() {
        // given
        PaymentApprovalRequest request = PaymentApprovalRequest.builder()
                .userId(3L)
                .amount(200.0)
                .currency(PaymentCurrencyEnum.USD)
                .merchantId(1L)
                .paymentMethod(PaymentMethodEnum.CREDIT_CARD)
                .paymentDetails(
                        PaymentDetails.builder()
                                .cardNumber("1234-1234-1234-4123")
                                .expiryDate("12/29")
                                .cvv(122)
                                .build()
                )
                .build();
        // when
        PaymentApprovalResponse response = paymentService.savePaymentApproval(request);

        // then
        assertEquals(response.getStatus(), PaymentStatusEnum.APPROVAL);
        assertEquals(response.getAmount(), 200.0);
        assertEquals(response.getCurrency(), PaymentCurrencyEnum.USD);
    }

    @Test
    void 결제_승인_요청_유저_못_찾음() {
        // given
        PaymentApprovalRequest request = PaymentApprovalRequest.builder()
                .userId(999L)
                .amount(200.0)
                .currency(PaymentCurrencyEnum.USD)
                .merchantId(1L)
                .paymentMethod(PaymentMethodEnum.CREDIT_CARD)
                .paymentDetails(
                        PaymentDetails.builder()
                                .cardNumber("1234-5678-9123-4567")
                                .expiryDate("12/24")
                                .cvv(123)
                                .build()
                )
                .build();
        // when
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            PaymentApprovalResponse response = paymentService.savePaymentApproval(request);
        });
        // then
        assertEquals(exception.getMessage(), "사용자를 찾을 수 없습니다.");
    }

    @Test
    void 결제_승인_요청_마켓_못_찾음() {
        // given
        PaymentApprovalRequest request = PaymentApprovalRequest.builder()
                .userId(1L)
                .amount(200.0)
                .currency(PaymentCurrencyEnum.USD)
                .merchantId(999L)
                .paymentMethod(PaymentMethodEnum.CREDIT_CARD)
                .paymentDetails(
                        PaymentDetails.builder()
                                .cardNumber("1234-5678-9123-4567")
                                .expiryDate("12/24")
                                .cvv(123)
                                .build()
                )
                .build();
        // when
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            PaymentApprovalResponse response = paymentService.savePaymentApproval(request);
        });
        // then
        assertEquals(exception.getMessage(), "상점을 찾을 수 없습니다.");
    }
}