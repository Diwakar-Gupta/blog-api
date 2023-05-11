package com.example.blogapi.security.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class JWTServiceTests {
    private static JWTService jwtService;

    private JWTService getJwtService(){
        if(jwtService == null){
            jwtService = new JWTService();
        }
        return jwtService;
    }

    @Test
    public void canCreateJWTForUserId(){
        var userId = 789;
        var jwtService = getJwtService();

        var jwtToken = jwtService.createJWT(userId);
        assertNotNull(jwtToken);
    }

    @Test
    public void checkAuthJWTForUserId(){
        var userId = 789;
        var jwtService = getJwtService();

        var jwtToken = jwtService.createJWT(userId);
        assertEquals(userId, jwtService.getUserIdFromJWT(jwtToken));
    }

    @Test
    public void checkAuthJWTForNull(){
        var jwtService = getJwtService();
        var randomToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        assertEquals(null, jwtService.getUserIdFromJWT(randomToken));
    }
}
