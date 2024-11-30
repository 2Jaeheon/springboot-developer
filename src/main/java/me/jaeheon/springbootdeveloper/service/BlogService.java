package me.jaeheon.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.jaeheon.springbootdeveloper.domain.Article;
import me.jaeheon.springbootdeveloper.dto.AddArticleRequest;
import me.jaeheon.springbootdeveloper.dto.UpdateArticleRequest;
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


    // 블로그 글 조회 메서드
    // findAll() 메서드는 JpaRepository에 존재함.
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    // 매칭한 메서드를 하나의 트렌잭션으로 묶음.
    // 스프링에서는 트랜젝션을 적용하기 위해서는 애너테이션 사용하면 됨
    // 이제 update() 메서드는 엔티티 필드 값이 바뀌면 제대로 된 값 수정을 보장함
    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
