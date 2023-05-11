package com.example.blogapi.users;

import com.example.blogapi.security.authtokens.AuthTokenService;
import com.example.blogapi.security.jwt.JWTService;
import com.example.blogapi.users.dto.CreateUserDTO;
import com.example.blogapi.users.dto.LoginUserDTO;
import com.example.blogapi.users.dto.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;
    private final AuthTokenService authTokenService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, JWTService jwtService, AuthTokenService authTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
        this.authTokenService = authTokenService;
    }

    public UserResponseDTO createUser(CreateUserDTO userDto){
        // TODO: Validate email
        // TODO: check if username already exists
        UserEntity user = this.modelMapper.map(userDto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        var savedUser = this.userRepository.save(user);
        var userResponseDTO = this.modelMapper.map(savedUser, UserResponseDTO.class);
        userResponseDTO.setToken(jwtService.createJWT(user.getId()));
        return userResponseDTO;
    }

    public UserResponseDTO loginUser(LoginUserDTO userDTO, AuthType token){
        var userEntity = this.userRepository.findByUsername(userDTO.getUsername());

        if(userEntity == null){
            throw new UserNotFoundException(userDTO.getUsername());
        }

        var passMatch = passwordEncoder.matches(userDTO.getPassword(), userEntity.getPassword());
        if(!passMatch){
            throw new IllegalArgumentException("Incorrect Password");
        }

        var userResponseDTO = this.modelMapper.map(userEntity, UserResponseDTO.class);

        switch (token){
            case AUTH_TOKEN -> userResponseDTO.setToken(authTokenService.createAuthToken(userEntity).toString());
            case JWT -> userResponseDTO.setToken(jwtService.createJWT(userEntity.getId()));
        }

        return userResponseDTO;
    }

    public UserEntity getUserById(Integer id) {
        UserEntity user = this.userRepository.findById(id).stream().findFirst().orElseThrow(() -> new UserNotFoundException(id));
        return user;
    }

    private static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(String username) {
            super("User with username: " + username + " not found");
        }
        public UserNotFoundException(Integer id) {
            super("User with id: " + id + " not found");
        }
    }

    static enum AuthType{
        JWT,
        AUTH_TOKEN
    }
}
