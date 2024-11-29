package me.jaeheon.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.jaeheon.springbootdeveloper.domain.Article;
import me.jaeheon.springbootdeveloper.dto.AddArticleRequest;
import me.jaeheon.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController // HTTP응답으로 개체 데이터를 JSON 형식으로 반환
public class BlogApiController {

    private final BlogService blogService;

    // HTTP메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")
    // @RequestBody는 HTTP 요청 시 응답에 해당하는 값을 @ReuqestBody애너테이션이 붙은 대상 객체인
    // AddArticleRequest에 매핑함.
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);

        // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        // ResponseEntity.status().body()는 응답 코드로 201, 즉, Created를 응답하고 저장된 객체 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }
}
