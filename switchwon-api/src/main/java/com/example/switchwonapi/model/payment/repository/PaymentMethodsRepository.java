package com.example.switchwonapi.model.payment.repository;

import com.example.switchwonapi.model.payment.PaymentMethodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodsRepository extends JpaRepository<PaymentMethodsEntity, Long> {
}
