package org.jaeheon.springbootdeveloper.config.oauth;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jaeheon.springbootdeveloper.domain.User;
import org.jaeheon.springbootdeveloper.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    // A user object is retrieved using the loadUser() method, which creates a user object based on information provided by the OAuth service.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        return user;
    }

    // If the user is in the user table, update it.
    // If not, create a new one and store it in the DB.
    private User saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        User user = userRepository.findByEmail(email)
            .map(entity -> entity.update(name))
            .orElse(User.builder()
                .email(email)
                .nickname(name)
                .build());

        return userRepository.save(user);
    }
}
