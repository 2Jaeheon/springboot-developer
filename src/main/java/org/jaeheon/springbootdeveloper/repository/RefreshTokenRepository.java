package org.jaeheon.springbootdeveloper.repository;

import java.util.Optional;
import org.jaeheon.springbootdeveloper.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUserId(Long userid);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
