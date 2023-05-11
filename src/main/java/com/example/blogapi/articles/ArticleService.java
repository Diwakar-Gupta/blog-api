package com.example.blogapi.articles;

import com.example.blogapi.users.UserEntity;
import com.example.blogapi.users.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserService userService;

    public ArticleService(ArticleRepository articleRepository, UserService userService) {
        this.articleRepository = articleRepository;
        this.userService = userService;
    }

    public Page<ArticleEntity> getArticles(Integer page, Integer size) {
        return this.articleRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }


    public Page<ArticleEntity> getArticlesByAuthor(Integer author, Integer page, Integer size) {
        return this.articleRepository.findAllByAuthorId(author, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public ArticleEntity getArticleBySlug(String slug) {
        ArticleEntity article = this.articleRepository.findBySlug(slug);
        if(article == null){
            throw new ArticleNotFoundException(slug);
        }
        return article;
    }

    public ArticleEntity createArticle(Integer authorId, String slug, String title, String subTitle, String body) {
        UserEntity author = this.userService.getUserById(authorId);
        ArticleEntity article = new ArticleEntity();
        article.setAuthor(author);
        article.setSlug(slug);
        article.setTitle(title);
        article.setSubTitle(subTitle);
        article.setBody(body);
        return this.articleRepository.save(article);
    }

    public ArticleEntity updateArticle(String articleSlug, String slug, String title, String subTitle, String body) {
        ArticleEntity article = this.getArticleBySlug(articleSlug);
        if(slug!= null)article.setSlug(slug);
        if(title!= null)article.setTitle(title);
        if(subTitle!= null)article.setSubTitle(subTitle);
        if(body!= null)article.setBody(body);
        return this.articleRepository.save(article);
    }

    public static class ArticleNotFoundException extends IllegalArgumentException {
        public ArticleNotFoundException(String slug) {
            super("Article with slug: "+ slug + " not found");
        }
    }
}
