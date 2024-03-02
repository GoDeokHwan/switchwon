package com.example.switchwonapi.model.user.repository.querydsl;

import com.example.switchwonapi.model.user.UsersEntity;

import java.util.Optional;

public interface UsersQueryDslRepository {
    Optional<UsersEntity> findByIdWithPaymentAccounts(Long id);

    Optional<UsersEntity> findByIdWithPaymentAccountsAndMethod(Long id);
}
