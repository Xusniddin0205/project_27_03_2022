package com.demo.demo.repository;

import com.demo.demo.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {

    Optional<RefreshToken> findById(Integer id);

    Optional<RefreshToken> findByToken(String token);
}
