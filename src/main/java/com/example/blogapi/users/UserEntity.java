package com.example.blogapi.users;

import com.example.blogapi.articles.ArticleEntity;
import com.example.blogapi.commons.Auditable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Getter
@Setter
@Entity(name = "users")
public class UserEntity extends Auditable {
    @Column(unique = true, nullable = false, length = 50)
    private String username;
    @Column(nullable = false, length = 50)
    private String email;
    String password;

    String bio;
    String image;

    @ManyToMany(mappedBy = "likedBy", fetch = FetchType.LAZY)
    List<ArticleEntity> likedArticles;

    @ManyToMany(mappedBy = "following", fetch = FetchType.LAZY)
    List<UserEntity> followers;
    @ManyToMany(fetch = FetchType.LAZY)
    List<UserEntity> following;
}
