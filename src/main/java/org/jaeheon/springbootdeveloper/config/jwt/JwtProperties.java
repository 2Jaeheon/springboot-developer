package org.jaeheon.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt")
// ConfigurationProperties Annotation used to retrieve property values from Java classes
// The jwt.issuer value mapped in application.yml is mapped to the issuer field, and the jwt.secret_key value is mapped to secretKey.
public class JwtProperties {

    private String issuer;
    private String secretKey;
}
