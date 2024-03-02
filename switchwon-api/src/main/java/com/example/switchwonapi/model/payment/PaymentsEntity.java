package com.example.switchwonapi.model.payment;

import com.example.switchwonapi.model.merchant.MerchantsEntity;
import com.example.switchwonapi.model.payment.enums.PaymentCurrencyEnum;
import com.example.switchwonapi.model.payment.enums.PaymentStatusEnum;
import com.example.switchwonapi.model.payment.enums.PaymentTypeEnum;
import com.example.switchwonapi.model.user.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Comment(value = "결제 정보")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name="payments")
public class PaymentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    @Comment(value = "ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    @Comment(value = "사용자 정보")
    private UsersEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_accounts_id")
    @Comment(value = "사용자 정보")
    private PaymentAccountsEntity paymentAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchants_id")
    @Comment(value = "상점 정보")
    private MerchantsEntity merchant;
    @Column
    @Enumerated(EnumType.STRING)
    @Comment(value = "결제 상태")
    private PaymentStatusEnum status;
    @Column
    @Enumerated(EnumType.STRING)
    @Comment(value = "결제 타입")
    private PaymentTypeEnum type;
    @Comment("사용자 결제정보")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "payment")
    private List<PaymentLogsEntity> paymentLogs;
    @Column
    @Comment(value = "금액")
    private double amount;
    @Column
    @Enumerated(EnumType.STRING)
    @Comment(value = "화폐타입")
    private PaymentCurrencyEnum currency;
    @Column
    @Comment(value = "요청일시")
    private LocalDateTime timestamp;
    @Column
    @Comment(value = "승인일시")
    private LocalDateTime approveTimestamp;

    public static PaymentsEntity ofPayment(UsersEntity user, PaymentAccountsEntity paymentAccount, MerchantsEntity merchant, Double amount, PaymentCurrencyEnum currency) {
        return PaymentsEntity.builder()
                .user(user)
                .paymentAccount(paymentAccount)
                .merchant(merchant)
                .status(PaymentStatusEnum.WAIT)
                .type(PaymentTypeEnum.PAYMENT)
                .paymentLogs(new ArrayList<>())
                .amount(amount)
                .currency(currency)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public void updatePaymentLog(List<PaymentLogsEntity> paymentLogs) {
        this.paymentLogs = paymentLogs;
    }

    public void updateFail() {
        this.status = PaymentStatusEnum.FAIL;
    }

    public void updateApproval() {
        this.status =PaymentStatusEnum.APPROVAL;
        this.approveTimestamp = LocalDateTime.now();
    }
}
