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
            TestEntity testEntity = new TestEntity();
            // 여기서는 TestService 클래스의 createTest 메서드를 호출하여 데이터베이스에 데이터를 저장합니다.
            // mac
            testService.createTest();
        };
    }
}
