package com.example.blogapi.users;

import com.example.blogapi.security.jwt.JWTService;
import com.example.blogapi.users.dto.CreateUserDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // this is used to test only DB layer not controller and service
//@SpringBootTest this can be used to test complete app very slow
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;

    private UserService getUserService(){
        return new UserService(
                this.userRepository,
                new BCryptPasswordEncoder(),
                new ModelMapper(),
                new JWTService(),
                null);
    }

    @Test
    void testCreateUser(){
        var userDTO = new CreateUserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setUsername("test name");
        userDTO.setPassword("test password");

        var savedUser = this.getUserService().createUser(userDTO);

        assertNotNull(savedUser);
        assertEquals("test name", savedUser.getUsername());
        assertEquals("test@example.com", savedUser.getEmail());
        assertNull(savedUser.getBio());
        assertNull(savedUser.getImage());
    }
}
