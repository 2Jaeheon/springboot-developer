package me.jaeheon.springbootdeveloper.domain;

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

@Entity // 엔티티로 지정 -> DB 테이블과 매핑됨.
@Getter // Getter를 자동으로 생성
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 자동으로 생성
// 기본 생성자의 접근제어자를 AccessLevel로 설정함.
public class Article {

    @Id // id 필드를 기본키로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    // title이라는 not null 컬럼과 매핑
    // updatable = false로 되어있어서 수정 안 됐음
    // 수정하려면 true가 되어야함. 기본값 == true
    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    // CreatedDate를 사용하면 엔티티가 생성될 때 created_at 컬럼에 저장
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 빌더 패턴으로 객체 생성 (Lombok)
    // 빌더 패턴을 사용하면 객체를 유연하고 직관적으로 생성이 가능
    // 빌더 패턴을 사용하면 어느 필드에 어떤 값이 들어가는지 명시적 파악이 가능함.

    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
