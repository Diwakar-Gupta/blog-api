package com.example.blogapi.articles;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {
    ArticleEntity findBySlug(String slug);
    Page<ArticleEntity> findAll(Pageable pageable);
    Page<ArticleEntity> findAllByAuthorId(Integer author, Pageable pageable);
}
