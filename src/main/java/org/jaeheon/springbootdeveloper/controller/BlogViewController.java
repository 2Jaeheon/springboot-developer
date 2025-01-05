package org.jaeheon.springbootdeveloper.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jaeheon.springbootdeveloper.domain.Article;
import org.jaeheon.springbootdeveloper.dto.ArticleListViewResponse;
import org.jaeheon.springbootdeveloper.dto.ArticleViewResponse;
import org.jaeheon.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticle(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
            .map(ArticleListViewResponse::new)
            .toList();

        model.addAttribute("articles", articles);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);

        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}