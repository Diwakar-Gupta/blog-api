package com.example.blogapi.security;

import com.example.blogapi.security.authtokens.AuthTokenAuthenticationFilter;
import com.example.blogapi.security.authtokens.AuthTokenService;
import com.example.blogapi.security.jwt.JWTAuthenticationFilter;
import com.example.blogapi.security.jwt.JWTService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private JWTService jwtService;
    private AuthTokenService authTokenService;

    public AppSecurityConfig(
            JWTService jwtService,
            AuthTokenService authTokenService
    ) {
        this.jwtService = jwtService;
        this.authTokenService = authTokenService;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO: In production this should be enabled
        // csrf: Cross Site Request Forgery: to send post request to this site you need to have csrf token from this site
        // cors: Cross Origin Request Forgery: Which frontends can send requests to this site.
        // http.csrf().disable().cors().disable();
        http.authorizeHttpRequests(requests -> requests
                .antMatchers(HttpMethod.GET, "/articles", "/articles/*").permitAll()
                .antMatchers(HttpMethod.GET, "/articles/*/comments", "/articles/*/comments/*").permitAll()
                .antMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()
                .antMatchers(HttpMethod.GET, "/users/*").permitAll()
                .anyRequest().authenticated())
                .addFilterBefore(new JWTAuthenticationFilter(jwtService), AnonymousAuthenticationFilter.class)
                .addFilterBefore(new AuthTokenAuthenticationFilter(authTokenService), AnonymousAuthenticationFilter.class)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;
//        super.configure(http); // above we have used custom authentication so don't call spring authentication
    }
}
