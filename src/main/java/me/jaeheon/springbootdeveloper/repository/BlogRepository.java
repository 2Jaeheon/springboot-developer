package me.jaeheon.springbootdeveloper.repository;

import me.jaeheon.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {

}
