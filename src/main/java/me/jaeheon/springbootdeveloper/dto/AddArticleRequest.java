package me.jaeheon.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jaeheon.springbootdeveloper.domain.Article;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {

    private String title;
    private String content;

    // 생성자를 사용해서 객체 생성
    // DTO를 엔티티로 만들어주는 메서드
    public Article toEntity() {
        return Article.builder()
            .title(title)
            .content(content)
            .build();
    }
}
