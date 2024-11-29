package me.jaeheon.springbootdeveloper.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // 엔티티로 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id // id 필드를 기본키로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    // title이라는 not null 컬럼과 매핑
    @Column(name = "title", updatable = false)
    private String title;

    @Column(name = "content", updatable = false)
    private String content;

    // 빌더 패턴으로 객체 생성 (Lombok)
    // 빌더 패턴을 사용하면 객체를 유연하고 직관적으로 생성이 가능
    // 빌더 패턴을 사용하면 어느 필드에 어떤 값이 들어가는지 명시적 파악이 가능함.

    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
