package com.example.blogapi.comments.dto;

import com.example.blogapi.articles.dto.MiniArticleResponseDTO;
import com.example.blogapi.users.dto.MiniUserResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDTO {
    Integer id;
    String title;
    String body;
    MiniUserResponseDTO author;
    MiniArticleResponseDTO article;
}
