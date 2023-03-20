package com.example.blogapi.users;

import com.example.blogapi.commons.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "users")
public class UserEntity extends Auditable {
    @Column(unique = true, length = 50)
    private String username;
    String password; // TODO need to hash

    String bio;
    String image;
}
