package com.example.blogapi.articles.dto;

import com.example.blogapi.users.dto.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ArticleResponseDTO {
    String slug;
    String title;
    String subTitle;
    String body;
    UserResponseDTO author;
    Date createdAt;
}
