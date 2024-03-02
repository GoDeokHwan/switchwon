package com.example.switchwonapi.model.payment.repository;

import com.example.switchwonapi.model.payment.PaymentEstimateLogsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentEstimateLogsRepository extends JpaRepository<PaymentEstimateLogsEntity, Long> {
}
