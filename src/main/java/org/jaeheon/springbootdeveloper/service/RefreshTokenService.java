package org.jaeheon.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.jaeheon.springbootdeveloper.domain.RefreshToken;
import org.jaeheon.springbootdeveloper.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new IllegalArgumentException("Unexpected Token"));
    }
}
