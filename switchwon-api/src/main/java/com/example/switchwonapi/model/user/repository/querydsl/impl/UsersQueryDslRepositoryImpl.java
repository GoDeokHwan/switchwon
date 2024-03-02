package com.example.switchwonapi.model.user.repository.querydsl.impl;

import com.example.switchwonapi.model.user.QUsersEntity;
import com.example.switchwonapi.model.user.UsersEntity;
import com.example.switchwonapi.model.user.repository.querydsl.UsersQueryDslRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.switchwonapi.model.payment.QPaymentAccountsEntity.paymentAccountsEntity;
import static com.example.switchwonapi.model.payment.QPaymentMethodsEntity.paymentMethodsEntity;
import static com.example.switchwonapi.model.user.QUsersEntity.usersEntity;
@Slf4j
@Repository
public class UsersQueryDslRepositoryImpl extends QuerydslRepositorySupport implements UsersQueryDslRepository {

    private final JPAQueryFactory factory;

    public UsersQueryDslRepositoryImpl(JPAQueryFactory factory) {
        super(UsersEntity.class);
        this.factory = factory;
    }

    @Override
    public Optional<UsersEntity> findByIdWithPaymentAccounts(Long id) {
        return Optional.ofNullable(from(usersEntity)
                .leftJoin(usersEntity.paymentAccountsEntities, paymentAccountsEntity).fetchJoin()
                .where(usersEntity.id.eq(id))
                .fetchOne()
        );
    }

    @Override
    public Optional<UsersEntity> findByIdWithPaymentAccountsAndMethod(Long id) {
        return Optional.ofNullable(from(usersEntity)
                .leftJoin(usersEntity.paymentAccountsEntities, paymentAccountsEntity).fetchJoin()
                .leftJoin(usersEntity.paymentMethods, paymentMethodsEntity).fetchJoin()
                .where(usersEntity.id.eq(id))
                .fetchOne()
        );
    }
}

