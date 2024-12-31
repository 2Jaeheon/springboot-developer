package org.jaeheon.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication is a convenience annotation that adds all of the following:
// - @SpringBootConfiguration: Indicates that this class provides Spring Boot-specific configuration.
// - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
// - @ComponentScan: Tells Spring to look for other components, configurations, and services in the package, allowing it to find the controllers.
@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}
