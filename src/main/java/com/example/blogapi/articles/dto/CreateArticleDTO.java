package com.example.blogapi.articles.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateArticleDTO {
    Integer authorId;
    String slug;
    String title;
    String subTitle;
    String body;
}
