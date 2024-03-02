package com.example.switchwonapi.model.payment.repository;

import com.example.switchwonapi.model.payment.PaymentAccountsEntity;
import com.example.switchwonapi.model.payment.PaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentAccountsRepository extends JpaRepository<PaymentAccountsEntity, Long> {
}
