package me.jaeheon.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.jaeheon.springbootdeveloper.domain.Article;
import me.jaeheon.springbootdeveloper.dto.AddArticleRequest;
import me.jaeheon.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    // save 메서드는 JpaRepository에서 지원하는 저장 메서드로
    // AddArticleRequest 클래스에 저장된 값들을 article 데이터베이스에 저장함.
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }
}
