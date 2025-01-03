package org.jaeheon.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.jaeheon.springbootdeveloper.domain.Article;
import org.jaeheon.springbootdeveloper.dto.AddArticleRequest;
import org.jaeheon.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

// this annotation is used to generate a constructor with required arguments
// final fields or fields with @NonNull annotation are considered as required arguments
@RequiredArgsConstructor
@Service
public class BlogService {

    // this filed must be initialized in the Constructor Injection
    // this ensure Dependency Injection
    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }
}
