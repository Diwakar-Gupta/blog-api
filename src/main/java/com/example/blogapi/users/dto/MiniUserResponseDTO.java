package com.example.blogapi.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiniUserResponseDTO{
    Integer id;
    String username;
    String image;
}