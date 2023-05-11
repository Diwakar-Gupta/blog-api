package com.example.blogapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@EnableJpaAuditing
public class BlogApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		// Hash + salt( cant remove it once mixed )
		// save password()
		// 			salt = "x length random string"
		// 			db.save( hash( password + salt) + salt )
		// verify password()
		// 			salt = "x sized suffix from DB"
		//			hash( password + salt ) == hashFromDB
		return new BCryptPasswordEncoder(); // one of the most popular encoder
	}
}
