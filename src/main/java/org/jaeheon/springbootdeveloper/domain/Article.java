package org.jaeheon.springbootdeveloper.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
// @NoArgsConstructor annotation is used to generate a no-args constructor
// this is useful when you want to create an instance of a class without any arguments
// access parameter is used to set the access level of the generated constructor
// in this case, the access level is set to protected
// this means that the constructor is only accessible to classes in the same package
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    // @Builder annotation is usable only when lombok plugin is installed
    // this annotation is used to generate a builder class for the annotated class
    // this is useful when you want to create an instance of a class with many fields
    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
