package com.xiaoxili.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xiaoxili.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmailIgnoreCase(String email);
    Boolean existsByEmail(String email);
}
