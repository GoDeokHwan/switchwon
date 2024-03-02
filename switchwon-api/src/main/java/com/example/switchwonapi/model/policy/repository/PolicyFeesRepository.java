package com.example.switchwonapi.model.policy.repository;

import com.example.switchwonapi.model.policy.PolicyFeesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PolicyFeesRepository extends JpaRepository<PolicyFeesEntity, Long> {

    Optional<PolicyFeesEntity> findTopByStartDateIsLessThanEqualAndEndDateIsGreaterThanEqualOrderByStartDateDesc(LocalDateTime startDate, LocalDateTime endDate);
}
