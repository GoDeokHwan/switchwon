package com.example.switchwonapi.model.payment;


import com.example.switchwonapi.model.payment.enums.PaymentMethodEnum;
import com.example.switchwonapi.model.user.UsersEntity;
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
@Table(name="payment_methods")
public class PaymentMethodsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    @Comment(value = "ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    @Comment(value = "사용자 정보")
    private UsersEntity user;
    @Column
    @Enumerated(EnumType.STRING)
    @Comment(value = "결제수단타입")
    private PaymentMethodEnum paymentMethod;
    @Column
    @Comment(value = "카드번호")
    private String cardNumber;
    @Column
    @Comment(value = "카드만료일")
    private String expiryDate;
    @Column
    @Comment(value = "CVV")
    private Integer cvv;
    @Column
    @CreationTimestamp
    @Comment(value = "생성일")
    private LocalDateTime createDate;
    @Column
    @UpdateTimestamp
    @Comment(value = "수정일")
    private LocalDateTime modifyDate;

    public static PaymentMethodsEntity of(UsersEntity usersEntity, PaymentMethodEnum paymentMethod, String cardNumber, String expiryDate, Integer cvv) {
        return PaymentMethodsEntity.builder()
                .user(usersEntity)
                .paymentMethod(paymentMethod)
                .cardNumber(cardNumber)
                .expiryDate(expiryDate)
                .cvv(cvv)
                .build();
    }
}
