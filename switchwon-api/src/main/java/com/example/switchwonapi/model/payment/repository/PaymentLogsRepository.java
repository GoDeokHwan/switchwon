package com.example.switchwonapi.model.payment.repository;

import com.example.switchwonapi.model.payment.PaymentLogsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentLogsRepository extends JpaRepository<PaymentLogsEntity, Long> {
}
