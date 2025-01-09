package org.jaeheon.springbootdeveloper.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import lombok.RequiredArgsConstructor;
import org.jaeheon.springbootdeveloper.config.jwt.TokenProvider;
import org.jaeheon.springbootdeveloper.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import org.jaeheon.springbootdeveloper.config.oauth.OAuth2SuccessHandler;
import org.jaeheon.springbootdeveloper.config.oauth.OAuth2UserCustomService;
import org.jaeheon.springbootdeveloper.repository.RefreshTokenRepository;
import org.jaeheon.springbootdeveloper.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
public class WebOAuthSecurityConfig {

    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
            .requestMatchers(toH2Console())
            .requestMatchers(
                new AntPathRequestMatcher("/img/**"),
                new AntPathRequestMatcher("/css/**"),
                new AntPathRequestMatcher("/js/**")
            );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            // Since authentication is done with a token, previously used form login and session are disabled.
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .sessionManagement(
                management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Add custom filter to check headers
            .addFilterBefore(tokenAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class)

            // The token reissue URL is set to be accessible without authentication. Rest of API URLs require authentication
            .authorizeRequests(auth ->
                auth.requestMatchers(new AntPathRequestMatcher("/api/token")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated()
                    .anyRequest().permitAll())

            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                // Store state related to Authorization requests
                .authorizationEndpoint(
                    authorizationEndpoint ->
                        authorizationEndpoint.authorizationRequestRepository(
                            oAuth2AuthorizationRequestBasedOnCookieRepository()))
                .userInfoEndpoint(
                    userInfoEndpoint ->
                        userInfoEndpoint.userService(oAuth2UserCustomService))
                // Handler to run upon successful authentication
                .successHandler(oAuth2SuccessHandler())
            )
            .exceptionHandling(
                exceptionHandling -> exceptionHandling.defaultAuthenticationEntryPointFor(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                    new AntPathRequestMatcher("/api/**")
                ))
            .build();
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider,
            refreshTokenRepository,
            oAuth2AuthorizationRequestBasedOnCookieRepository(),
            userService
        );
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
