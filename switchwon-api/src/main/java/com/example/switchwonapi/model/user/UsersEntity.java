package com.example.switchwonapi.model.user;

import com.example.switchwonapi.model.payment.PaymentAccountsEntity;
import com.example.switchwonapi.model.payment.PaymentMethodsEntity;
import com.example.switchwonapi.model.payment.PaymentsEntity;
import com.example.switchwonapi.model.payment.enums.PaymentMethodEnum;
import jakarta.persistence.*;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Comment(value = "사용자 정보")
@Getter
@Entity
@Table(name="users")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    @Comment(value = "ID")
    private Long id;
    @Column(length = 30)
    @Comment(value = "이름")
    private String name;
    @Comment("사용자 결제정보")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<PaymentAccountsEntity> paymentAccountsEntities;
    @Comment("결제 정보")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<PaymentMethodsEntity> paymentMethods;
    @Column
    @CreationTimestamp
    @Comment(value = "생성일")
    private LocalDateTime createDate;
    @Column
    @UpdateTimestamp
    @Comment(value = "수정일")
    private LocalDateTime modifyDate;

    // 1:1 관계
    public PaymentAccountsEntity getPaymentAccount() {
        if (CollectionUtils.isNotEmpty(this.getPaymentAccountsEntities())) {
            return this.getPaymentAccountsEntities().get(0);
        } else {
            return null;
        }
    }

    public Optional<PaymentMethodsEntity> findPaymentMethod(PaymentMethodEnum paymentMethod, String cardNumber, String expiryDate, Integer cvv) {
        if (CollectionUtils.isNotEmpty(this.getPaymentMethods())) {
            return this.getPaymentMethods()
                    .stream()
                    .filter(paymentMethods -> {
                        return paymentMethods.getPaymentMethod().equals(paymentMethod)
                                && paymentMethods.getCardNumber().equals(cardNumber)
                                && paymentMethods.getExpiryDate().equals(expiryDate)
                                && paymentMethods.getCvv().equals(cvv);
                    })
                    .findAny();
        }
        return Optional.empty();
    }

    public PaymentMethodsEntity addPaymentMethods(PaymentMethodEnum paymentMethod, String cardNumber, String expiryDate, Integer cvv) {
        if (CollectionUtils.isEmpty(this.getPaymentMethods())) {
            this.paymentMethods = new HashSet<>();
        }
        PaymentMethodsEntity paymentMethods = PaymentMethodsEntity.of(
                this
                , paymentMethod
                , cardNumber
                , expiryDate
                , cvv
        );
        this.paymentMethods.add(paymentMethods);
        return paymentMethods;
    }
}
