package me.jaeheon.springbootdeveloper.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.jaeheon.springbootdeveloper.domain.Article;
import me.jaeheon.springbootdeveloper.dto.ArticleListViewResponse;
import me.jaeheon.springbootdeveloper.dto.ArticleViewResponse;
import me.jaeheon.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

// 주로 불변 객체를 생성할 때 사용되며,
// 필수 인자만을 받는 생성자를 자동 생성
// final 필드나, @NonNull 어노테이션이 붙은 필드를 기반으로 생성

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
            .map(ArticleListViewResponse::new)
            .toList();

        // addAttribute() 메서드를 이용해서 모델에 값 저장
        model.addAttribute("articles", articles);

        // articleList는 resource/templates/articleList.html을 찾도록하는 뷰이름
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
