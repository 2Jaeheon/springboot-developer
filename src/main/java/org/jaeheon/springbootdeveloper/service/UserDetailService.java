package org.jaeheon.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.jaeheon.springbootdeveloper.domain.User;
import org.jaeheon.springbootdeveloper.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
// UserDetailsService is Interface to retrieve user information from Spring Security
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException((email)));
    }
}
