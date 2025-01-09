package org.jaeheon.springbootdeveloper.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
// @NoArgsConstructor annotation is used to generate a no-args constructor
// this is useful when you want to create an instance of a class without any arguments
// access parameter is used to set the access level of the generated constructor
// in this case, the access level is set to protected
// this means that the constructor is only accessible to classes in the same package
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// @EntityListeners annotation is used to specify the callback listener classes for an entity
// in this case, the AuditingEntityListener class is specified as the callback listener class
@EntityListeners(AuditingEntityListener.class)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "author", nullable = false)
    private String author;


    // created date is automatically set when the entity is created
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // modified date is automatically updated when the entity is updated
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // @Builder annotation is usable only when lombok plugin is installed
    // this annotation is used to generate a builder class for the annotated class
    // this is useful when you want to create an instance of a class with many fields
    @Builder
    public Article(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
