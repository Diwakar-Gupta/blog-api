package com.example.blogapi.security.authtokens;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthTokenRepository extends JpaRepository<AuthTokenEntity, UUID> {
}
