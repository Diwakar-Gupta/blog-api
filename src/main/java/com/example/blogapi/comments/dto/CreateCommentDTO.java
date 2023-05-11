package com.example.blogapi.comments.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentDTO {
    Integer authorId;
    String title;
    String body;
}
