package org.jaeheon.springbootdeveloper.config.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import io.jsonwebtoken.Jwts;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import org.jaeheon.springbootdeveloper.domain.User;
import org.jaeheon.springbootdeveloper.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateToken(): 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {
        // given
        // Create a test user to add user information to the token
        User testUser = userRepository.save(User.builder()
            .email("user@gmail.com")
            .password("test")
            .build());

        // when
        // Create a token by calling the token provider's generateToken() method.
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        // then
        // Decrypt the token through the jjwt library.
        // When creating a token, check whether the ID value entered as a claim is the same as the user ID created in the given clause.
        Long userId = Jwts.parser()
            .setSigningKey(jwtProperties.getSecretKey())
            .parseClaimsJws(token)
            .getBody()
            .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    @DisplayName("validToken(): 만료된 토큰인 때에 유효성 검증에 실패한다.")
    @Test
    void validToken_invalidToken() {
        // given
        // When creating a token using the jjwt library,
        // the expiration time is created as a token that has already expired.
        String token = JwtFactory.builder()
            .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
            .build()
            .createToken(jwtProperties);

        // when
        // Calls the token provider's validateToken() method to verify whether the token is valid and returns the result.
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("validToken(): 유효한 토큰인 때에 유효성 검증에 성공한다.")
    @Test
    void validToken_validToken() {
        // given
        // Create a token using the jjwt library.
        // The expiration time is 14 days later, creating an unexpired token.
        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);

        // when
        // Calls the token provider's validateToken() method to verify whether the token is valid and returns the result.
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("getAuthentication(): 토큰 기반으로 인증 정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        // given
        // Create a token with the subject of the token as user@email.com.
        String userEmail = "user@email.com";
        String token = JwtFactory.builder()
            .subject(userEmail)
            .build()
            .createToken(jwtProperties);

        // when
        // An authentication object is returned by calling the token provider's getAuthentication() method.
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then
        // Get the user name of the returned authentication object
        // and check whether the subject value set in the given clause is equal to "user@email.com"
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(
            userEmail);
    }

    @DisplayName("getUserId(): 토큰 기반으로 유저 ID를 가져올 수 있다.")
    @Test
    void getUserId() {
        // given
        // Add a claim for user ID with key "id" and value 1.
        Long userId = 1L;
        String token = JwtFactory.builder()
            .claims(Map.of("id", userId))
            .build()
            .createToken(jwtProperties);

        // when
        // Call the token provider's getUserId() method to get the user ID from the token.
        Long userIdByToken = tokenProvider.getUserId(token);

        // then
        // Check whether the user ID value obtained from the token is equal to 1.
        assertThat(userIdByToken).isEqualTo(userId);
    }
}