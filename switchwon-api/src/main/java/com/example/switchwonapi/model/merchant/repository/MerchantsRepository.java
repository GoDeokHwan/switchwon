package com.example.switchwonapi.model.merchant.repository;

import com.example.switchwonapi.model.merchant.MerchantsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantsRepository extends JpaRepository<MerchantsEntity, Long> {
}
