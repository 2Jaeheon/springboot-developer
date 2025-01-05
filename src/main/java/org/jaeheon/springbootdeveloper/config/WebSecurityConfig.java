package org.jaeheon.springbootdeveloper.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import lombok.RequiredArgsConstructor;
import org.jaeheon.springbootdeveloper.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailService userDetailService;

    // Disable Spring Security features
    @Bean
    public WebSecurityCustomizer configure() {
        // Exclude specified request path from security filters in Spring Security
        // The ignoring() method is used to exclude the specified request path from the security filter.
        return (web) -> web.ignoring()
            // requestMatcher() is a method that sets matching conditions.
            // Method determines whether a specific HTTP request (e.g. URL path, HTTP method, etc.) is subject to security settings.
            .requestMatchers(toH2Console())
            .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

    /*
    1.	/login, /signup.html, /user 경로는 누구나 접근 가능.
	2.	다른 모든 경로는 로그인해야 접근 가능.
	3.	로그인 시 /login 페이지를 사용하고, 성공하면 /articles로 이동.
	4.	로그아웃 시 /login으로 이동하며, 세션 정보를 삭제.
	5.	CSRF 보호를 비활성화하여 간단한 API 환경을 지원.
	*/

    // Configuring web-based security for specific HTTP requests
    // The filterChain() method is used to configure the security filter chain.
    @Bean
    // HttpSecurity is an object that plays a key role in security settings.
    // Here, the security rules of the application are defined through an object called http.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            // requestMatchers: Specifies a specific URL path.
            // For example, the paths /login, /signup.html, and /user are set to be accessible to anyone (even users who are not logged in).
            // This is denoted by permitAll()
            .authorizeRequests(auth -> auth
                .requestMatchers(
                    new AntPathRequestMatcher("/login"),
                    new AntPathRequestMatcher("/signup"),
                    new AntPathRequestMatcher("/user")
                ).permitAll()
                // anyRequest().authenticated(): Sets all other requests not specified
                // above to be accessible only to authenticated users (logged in users).
                .anyRequest().authenticated())

            .formLogin(formLogin -> formLogin
                // loginPage("/login"): Shows the /login page when the user needs to log in.
                // This configuration allows us to use our defined /login page
                // instead of the default login page provided by Spring Security.
                .loginPage("/login")
                // defaultSuccessUrl("/articles"): When the user successfully logs in,
                // the user goes to the /articles page by default.
                .defaultSuccessUrl("/articles")
            )
            .logout(logout -> logout
                // logoutSuccessUrl("/login"): If the user successfully logs out, the user goes to the /login page.
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
            )
            // csrf: Disable CSRF protection.
            // CSRF protects user requests from malicious manipulation,
            // but is often disabled in environments such as REST APIs.
            .csrf(AbstractHttpConfigurer::disable)
            .build();
    }


    /*
    1.	사용자가 로그인 시, ID와 비밀번호를 입력합니다.
	2.	AuthenticationManager는 입력받은 정보를 DaoAuthenticationProvider로 전달합니다.
	3.	DaoAuthenticationProvider는 UserDetailService를 사용해 데이터베이스에서 사용자의 정보를 조회합니다.
	4.	입력받은 비밀번호를 암호화(BCryptPasswordEncoder)하여, 저장된 비밀번호와 비교합니다.
	5.	정보가 일치하면 인증 성공, 그렇지 않으면 인증 실패를 반환합니다.
	*/

    @Bean
    // AuthenticationManager checks the authentication information entered by the user and decides whether to authenticate the user based on this.
    // HttpSecurity http: Security settings object. Although not used directly here, it can be used when security settings are needed.
    // BCryptPasswordEncoder bCryptPasswordEncoder: Password encoder object.
    // UserDetailService userDetailService: A service that retrieves user information. A role that retrieves user ID, password, permissions, etc. from a database or other storage.
    public AuthenticationManager authenticationManager(HttpSecurity http,
        BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService)
        throws Exception {
        // DaoAuthenticationProvider: Provider that searches and authenticates user information in database or memory
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // setUserDetailsService(userDetailService): Retrieve user information using UserDetailService.
        authProvider.setUserDetailsService(userDetailService);
        // etPasswordEncoder(bCryptPasswordEncoder): Set to compare user passwords in encrypted form.
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider);
    }


    @Bean
    // BCryptPasswordEncoder: Password encoder that encrypts and decrypts passwords.
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
