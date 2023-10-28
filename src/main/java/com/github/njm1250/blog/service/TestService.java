package com.github.njm1250.blog.service;

import com.github.njm1250.blog.entity.TestEntity;
import com.github.njm1250.blog.repository.TestRepository;
import org.aspectj.weaver.ast.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    private final TestRepository testRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Transactional
    public TestEntity createTest() {
        TestEntity test = new TestEntity();
        securityTest();
        return testRepository.save(test); // 데이터베이스에 엔티티를 저장합니다.
    }

    //TODO 나중엔 userDTO에 rawPassword를 담아서 getPassword로 받고, 그걸 다시 encode한 다음 setPassword로 해서
    //TODO 그 userDTO를 다시 서비스에 넘겨주기
    public void securityTest() {
        String isSame = "False";
        String password = "abc12345";
        String encodedPassword = passwordEncoder.encode(password);
        if(checkPassword(password, encodedPassword)) {
            isSame = "True";
        }
        logger.debug("encoded password : {}", encodedPassword);
        logger.debug("check password : {}", isSame);
    }

    public boolean checkPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}

