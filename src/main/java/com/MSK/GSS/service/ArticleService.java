package com.MSK.GSS.service;

import com.MSK.GSS.dao.ArticleRepository;
import com.MSK.GSS.domain.Article;
import com.MSK.GSS.domain.Member;
import com.MSK.GSS.dto.article.ArticleSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public void save(ArticleSaveForm articleSaveform, Member member) {

        Article article = Article.createArticle(
                articleSaveform.getTitle(),
                articleSaveform.getBody()
        );

        article.setMember(member);
        articleRepository.save(article);

    }
}
