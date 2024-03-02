package com.example.switchwonapi.model.payment;

import com.example.switchwonapi.model.payment.enums.PaymentCurrencyEnum;
import com.example.switchwonapi.model.user.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Comment(value = "계좌 정보")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name="payment_accounts")
public class PaymentAccountsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    @Comment(value = "ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    @Comment(value = "사용자 정보")
    private UsersEntity user;
    @Comment("사용자 결제정보")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentAccount")
    private List<PaymentsEntity> payments;
    @Column
    @Comment(value = "금액")
    private double balance;
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

    public static PaymentAccountsEntity of(UsersEntity user) {
        return PaymentAccountsEntity.builder()
                .user(user)
                .payments(new ArrayList<>())
                .balance(0.0)
                .currency(PaymentCurrencyEnum.USD)
                .build();
    }

    public void minusAmount(double amount) {
        if (this.getBalance() - amount < 0) {
            throw new RuntimeException("잔액이 부족합니다.");
        }
        this.balance -= amount;
    }

    public void plusAmount(double amount) {
        this.balance += amount;
    }
}
