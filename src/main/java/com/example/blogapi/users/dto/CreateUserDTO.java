package com.example.blogapi.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO {
    String email;
    String username;
    String password;
}
