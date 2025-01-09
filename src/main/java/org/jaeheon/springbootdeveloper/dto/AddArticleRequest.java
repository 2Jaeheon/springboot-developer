package org.jaeheon.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jaeheon.springbootdeveloper.domain.Article;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {

    private String title;
    private String content;

    // toEntity() method is used to convert the AddArticleRequest object to an Article object
    // AddArticleRequest -> Article
    public Article toEntity(String author) {
        // return new instance of Article with the title and content
        return Article.builder()
            .title(title)
            .content(content)
            .author(author)
            .build();
    }
}
