package io.dzung.mvcauthdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.dzung.mvcauthdemo.entity.VerificationToken;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    @Query("SELECT t FROM VerificationToken t WHERE t.user.id = :userId")
    Optional<VerificationToken> findByUserId(Long userId);
}
