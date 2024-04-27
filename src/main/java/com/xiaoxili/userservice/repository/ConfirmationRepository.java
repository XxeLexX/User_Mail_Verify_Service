package com.xiaoxili.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xiaoxili.userservice.model.Confirmation;

public interface ConfirmationRepository extends JpaRepository<Confirmation, Long>{
    Confirmation findByToken(String token);
}
