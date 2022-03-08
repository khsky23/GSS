package com.MSK.GSS.dao;

import com.MSK.GSS.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}