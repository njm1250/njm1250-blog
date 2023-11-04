package com.github.njm1250.blog.service;

import com.github.njm1250.blog.entity.User;
import com.github.njm1250.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class AppStartupRunner {


    @Autowired
    private UserRepository userRepository;

    @Bean
    public CommandLineRunner commandLineRunner(TestService testService) {
        return args -> {
            // 애플리케이션이 시작될 때 실행할 코드
            String usernameToTest = "test01";
            Optional<User> foundUser = userRepository.findByUsername(usernameToTest);

            if (foundUser.isPresent()) {
                System.out.println("Found user: " + foundUser.get());
            } else {
                System.out.println("User not found with username: " + usernameToTest);
            }

        };
    }
}
