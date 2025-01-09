package org.jaeheon.springbootdeveloper.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Builder
    public User(String email, String password, String auth, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    // UserDetails interface methods
    // this method returns the authorities granted to the user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
        // Returns a list of privileges held by the user. In the current example,
        // since there are no permissions other than the user, only user permissions are returned.
    }

    @Override
    public String getUsername() {
        // Returns an identifiable unique username
        return email;
    }

    @Override
    public String getPassword() {
        // Returns the user's password. At this time, the saved password is encrypted and stored.
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Check if your account has expired
        return true;
        // return true; is non-expired account
    }

    @Override
    public boolean isAccountNonLocked() {
        // Check if your account is locked
        return true;
        // return true; is non-locked account
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Check if your password has expired
        return true;
        // return true; is non-expired password
    }

    // Returns account availability
    @Override
    public boolean isEnabled() {
        // this is logic for account availability
        return true;
        // return true; is enabled account
    }

    public User update(String nickname) {
        this.nickname = nickname;
        
        return this;
    }
}
