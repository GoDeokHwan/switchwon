package com.example.switchwonapi.model.payment;

import com.example.switchwonapi.model.merchant.MerchantsEntity;
import com.example.switchwonapi.model.payment.enums.PaymentCurrencyEnum;
import com.example.switchwonapi.model.policy.PolicyFeesEntity;
import com.example.switchwonapi.model.user.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Optional;

@Comment(value = "결제 예상 정보")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name="payment_estimate_logs")
public class PaymentEstimateLogsEntity {
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
    @JoinColumn(name = "merchants_id")
    @Comment(value = "상점 정보")
    private MerchantsEntity merchant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_fees_id")
    @Comment(value = "정책(수수료)")
    private PolicyFeesEntity policyFees;
    @Column
    @Comment(value = "금액")
    private double amount;
    @Column
    @Enumerated(EnumType.STRING)
    @Comment(value = "화폐타입")
    private PaymentCurrencyEnum currency;
    @Column
    @Comment(value = "수수료")
    private double fees;
    @Column
    @Comment(value = "예상금액")
    private double estimatedTotal;
    @Column
    @CreationTimestamp
    @Comment(value = "생성일")
    private LocalDateTime createDate;
    @Column
    @UpdateTimestamp
    @Comment(value = "수정일")
    private LocalDateTime modifyDate;

    public static PaymentEstimateLogsEntity of(UsersEntity user, MerchantsEntity merchant, Double amount, PaymentCurrencyEnum currency, Optional<PolicyFeesEntity> policyFees) {
        PolicyFeesEntity policyFeesEntity = policyFees.orElseGet(() -> null);
        double fees = calFees(amount, policyFees.isPresent() ? policyFeesEntity.getFees() : 0.0);
        return PaymentEstimateLogsEntity.builder()
                .user(user)
                .merchant(merchant)
                .policyFees(policyFeesEntity)
                .amount(amount)
                .currency(currency)
                .fees(fees)
                .estimatedTotal(amount + fees)
                .build();
    }

    private static double calFees(double amount, double fees) {
        if (fees > 0) {
            return Math.ceil(amount * fees);
        } else {
            return 0.0;
        }
    }
}
