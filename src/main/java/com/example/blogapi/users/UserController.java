package com.example.blogapi.users;

import com.example.blogapi.users.dto.CreateUserDTO;
import com.example.blogapi.users.dto.LoginUserDTO;
import com.example.blogapi.users.dto.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("")
    ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserDTO userDto){
        var savedUser = this.userService.createUser(userDto);
        return ResponseEntity.created(URI.create("/users/"+savedUser.getId())).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(
            @RequestBody LoginUserDTO loginUserDTO,
            @RequestParam(value = "token", defaultValue = "jwt") String token){
        // if token = 'jwt'(default) generate JWT token; if token = 'auth_token' generate auth_token.
        var authType = UserService.AuthType.JWT;
        if(token.equals("auth_token")){
            authType = UserService.AuthType.AUTH_TOKEN;
        }
        var user = userService.loginUser(loginUserDTO, authType);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserResponseDTO> getUser(@AuthenticationPrincipal Integer authId, @PathVariable Integer id){
        var user = this.userService.getUserById(id);
        var userDTO = this.modelMapper.map(user, UserResponseDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
