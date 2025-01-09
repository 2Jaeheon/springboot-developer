package org.jaeheon.springbootdeveloper.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jaeheon.springbootdeveloper.domain.Article;
import org.jaeheon.springbootdeveloper.dto.AddArticleRequest;
import org.jaeheon.springbootdeveloper.dto.UpdateArticleRequest;
import org.jaeheon.springbootdeveloper.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// this annotation is used to generate a constructor with required arguments
// final fields or fields with @NonNull annotation are considered as required arguments
@RequiredArgsConstructor
@Service
public class BlogService {

    // this filed must be initialized in the Constructor Injection
    // this ensure Dependency Injection
    private final BlogRepository blogRepository;

    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

    public Article save(AddArticleRequest request, String userName) {
        return blogRepository.save(request.toEntity(userName));
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not fount: " + id));
    }

    public void delete(long id) {
        Article article = blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        authorizeArticleAuthor(article);

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
