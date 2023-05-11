package com.example.blogapi.articles.dto;

import com.example.blogapi.users.dto.MiniUserResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiniArticleResponseDTO{
    String slug;
    String title;
    MiniUserResponseDTO author;
}