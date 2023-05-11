package com.example.blogapi.articles;

import com.example.blogapi.comments.CommentEntity;
import com.example.blogapi.commons.Auditable;
import com.example.blogapi.users.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
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

    @ManyToMany(fetch = FetchType.LAZY)
    List<UserEntity> likedBy;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    List<CommentEntity> comments;
}
