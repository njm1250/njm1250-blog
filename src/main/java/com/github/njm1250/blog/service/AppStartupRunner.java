package com.github.njm1250.blog.service;

import com.github.njm1250.blog.entity.TestEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppStartupRunner {

    @Bean
    public CommandLineRunner commandLineRunner(TestService testService) {
        return args -> {
            // 애플리케이션이 시작될 때 실행할 코드
        };
    }
}
