package com.example.blogapi.security.authtokens;

import com.example.blogapi.users.UserEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthTokenService {
    private AuthTokenRepository authTokenRepository;

    public AuthTokenService(AuthTokenRepository authTokenRepository) {
        this.authTokenRepository = authTokenRepository;
    }

    public UUID createAuthToken(UserEntity userEntity){
        AuthTokenEntity authTokenEntity = new AuthTokenEntity();
        authTokenEntity.setUser(userEntity);
        var savedAuthToken = authTokenRepository.save(authTokenEntity);
        return savedAuthToken.getId();
    }

    public UserEntity getUserFromAuthToken(UUID uuid){
        var savedAuthToken = authTokenRepository.findById(uuid)
                .orElseThrow(() -> new BadCredentialsException("Invalid Auth Token"));
        return savedAuthToken.getUser();
    }
}
