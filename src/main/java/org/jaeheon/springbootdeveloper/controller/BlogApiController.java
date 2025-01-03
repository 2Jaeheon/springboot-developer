package org.jaeheon.springbootdeveloper.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jaeheon.springbootdeveloper.domain.Article;
import org.jaeheon.springbootdeveloper.dto.AddArticleRequest;
import org.jaeheon.springbootdeveloper.dto.ArticleResponse;
import org.jaeheon.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
            .stream()
            .map(ArticleResponse::new)
            .toList();

        return ResponseEntity.ok()
            .body(articles);
    }
}
