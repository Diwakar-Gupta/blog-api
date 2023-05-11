package com.example.blogapi.comments;

import com.example.blogapi.articles.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> getByArticle(ArticleEntity article);
}
