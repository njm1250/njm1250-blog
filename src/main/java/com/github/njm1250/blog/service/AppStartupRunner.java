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

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {

        };
    }
}
