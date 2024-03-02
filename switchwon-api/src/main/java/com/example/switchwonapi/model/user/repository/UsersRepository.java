package com.example.switchwonapi.model.user.repository;

import com.example.switchwonapi.model.user.UsersEntity;
import com.example.switchwonapi.model.user.repository.querydsl.UsersQueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long>, UsersQueryDslRepository {
}
