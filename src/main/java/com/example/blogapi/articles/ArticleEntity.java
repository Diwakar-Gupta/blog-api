package com.example.blogapi.articles;

import com.example.blogapi.commons.Auditable;
import com.example.blogapi.users.UserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "articles")
public class ArticleEntity extends Auditable {
    @Column(unique = true, nullable = false, length = 150)
    String slug;
    @Column(nullable = false, length = 200)
    String title;
    String subTitle;
    @Column(nullable = false, length = 1000)
    String body;

    @ManyToOne
    UserEntity author;
}
