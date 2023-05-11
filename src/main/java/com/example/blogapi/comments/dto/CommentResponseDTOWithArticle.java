package com.example.blogapi.comments.dto;

import com.example.blogapi.articles.dto.MiniArticleResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDTOWithArticle {
    Integer id;
    String title;
    String body;
    MiniArticleResponseDTO article;
}