package me.jaeheon.springbootdeveloper.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.jaeheon.springbootdeveloper.domain.Article;
import me.jaeheon.springbootdeveloper.dto.AddArticleRequest;
import me.jaeheon.springbootdeveloper.dto.ArticleResponse;
import me.jaeheon.springbootdeveloper.dto.UpdateArticleRequest;
import me.jaeheon.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        // 요청이 들어오면 findAll() 메서드를 호출한 뒤 응답용 객체인
        // ArticleResponse로 파싱해서 body에 담아 클라이언트에게 전송(stream)이용
        List<ArticleResponse> articles = blogService.findAll().stream()
            .map(article -> new ArticleResponse(article))
            // .map(ArticleResponse::new)
            .toList();

        // 200 OK HTTP 상태 코드를 포함한 응답을 생성
        return ResponseEntity.ok().body(articles);
    }

    @GetMapping("/api/articles/{id}")
    // @PathVariable 애너테이션은 URL에서 값을 가져오는 애너테이션임.
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);

        return ResponseEntity.ok().body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);

        return ResponseEntity.ok().build();
    }


    // put 요청이 오면 RequestBody 정보가 request로 넘어옴
    // 서비스 클래스의 update() 메서드에 id와 request를 넘겨줌
    // 응답 값은 body에 담아 전송함.
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
        @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);
        
        return ResponseEntity.ok()
            .body(updatedArticle);
    }
}
