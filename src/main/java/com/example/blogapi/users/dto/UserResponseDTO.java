package com.example.blogapi.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    Integer id;
    String username;
    String email;
    String bio;
    String image;
    String token;
}
