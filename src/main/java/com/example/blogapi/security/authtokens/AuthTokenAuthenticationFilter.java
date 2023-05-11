package com.example.blogapi.security.authtokens;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class AuthTokenAuthenticationFilter extends AuthenticationFilter {

    public AuthTokenAuthenticationFilter(AuthTokenService authTokenService) {
        super(new AuthTokenManager(authTokenService), new AuthTokenConverter());

        setSuccessHandler(((request, response, authentication) -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }));
    }

    static class AuthTokenConverter implements AuthenticationConverter {
        @Override
        public Authentication convert(HttpServletRequest request) {
            // here we have used a api=key named "X-Auth-Token"
            if(request.getHeader("X-Auth-Token") != null){
                String token = request.getHeader("X-Auth-Token");
                return new AuthTokenAuthentication(token);
            }
            return null;
        }
    }

    static class AuthTokenManager implements AuthenticationManager{
        private AuthTokenService authTokenService;

        public AuthTokenManager(AuthTokenService authTokenService) {
            this.authTokenService = authTokenService;
        }

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            if(authentication instanceof AuthTokenAuthentication){
                var authTokenAuthentication = (AuthTokenAuthentication) authentication;
                String token = authTokenAuthentication.getCredentials();

                if(token != null) {
                    var userEntity = this.authTokenService.getUserFromAuthToken(UUID.fromString(token));
                    if(userEntity != null){
                        authTokenAuthentication.setUserId(userEntity.getId());
                        return authTokenAuthentication;
                    }
                }
            }
            return null;
        }
    }
}
