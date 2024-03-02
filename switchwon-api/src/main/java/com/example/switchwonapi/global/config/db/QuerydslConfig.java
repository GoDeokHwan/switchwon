package com.example.switchwonapi.global.config.db;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.Querydsl;

@Configuration
public class QuerydslConfig {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQueryFactory getJpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}