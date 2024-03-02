package com.example.switchwonapi.model.payment;

import com.example.switchwonapi.model.merchant.MerchantsEntity;
import com.example.switchwonapi.model.payment.enums.PaymentCurrencyEnum;
import com.example.switchwonapi.model.payment.enums.PaymentMethodEnum;
import com.example.switchwonapi.model.payment.enums.PaymentStatusEnum;
import com.example.switchwonapi.model.payment.enums.PaymentTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Comment(value = "결제 정보")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name="payment_logs")
public class PaymentLogsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    @Comment(value = "ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payments_id")
    @Comment(value = "사용자 정보")
    private PaymentsEntity payment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchants_id")
    @Comment(value = "상점 정보")
    private MerchantsEntity merchant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_methods_id")
    @Comment(value = "결제수단정보")
    private PaymentMethodsEntity paymentMethod;
    @Column
    @Enumerated(EnumType.STRING)
    @Comment(value = "결제 상태")
    private PaymentStatusEnum status;
    @Column
    @Enumerated(EnumType.STRING)
    @Comment(value = "결제 타입")
    private PaymentTypeEnum type;
    @Column
    @Comment(value = "금액")
    private double amount;
    @Column
    @Enumerated(EnumType.STRING)
    @Comment(value = "화폐타입")
    private PaymentCurrencyEnum currency;
    @Column
    @CreationTimestamp
    @Comment(value = "생성일")
    private LocalDateTime createDate;
    @Column
    @UpdateTimestamp
    @Comment(value = "수정일")
    private LocalDateTime modifyDate;


    public static PaymentLogsEntity ofPayment(PaymentsEntity payments, MerchantsEntity merchant, PaymentMethodsEntity paymentMethod, double amount, PaymentCurrencyEnum currency) {
        payments.getPaymentAccount().minusAmount(amount);
        return PaymentLogsEntity.builder()
                .payment(payments)
                .merchant(merchant)
                .paymentMethod(paymentMethod)
                .status(PaymentStatusEnum.WAIT)
                .type(PaymentTypeEnum.PAYMENT)
                .amount(amount)
                .currency(currency)
                .build();
    }

    public static PaymentLogsEntity ofCharge(PaymentsEntity payments, MerchantsEntity merchant, PaymentMethodsEntity paymentMethod, double amount, PaymentCurrencyEnum currency) {
        payments.getPaymentAccount().plusAmount(amount);
        return PaymentLogsEntity.builder()
                .payment(payments)
                .merchant(merchant)
                .paymentMethod(paymentMethod)
                .status(PaymentStatusEnum.WAIT)
                .type(PaymentTypeEnum.CHARGE)
                .amount(amount)
                .currency(currency)
                .build();
    }

    public void updateFail() {
        this.status = PaymentStatusEnum.FAIL;
    }

    public void updateApproval() {
        this.status = PaymentStatusEnum.APPROVAL;
    }
}
