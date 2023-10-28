package com.github.njm1250.blog.service;

import com.github.njm1250.blog.entity.TestEntity;
import com.github.njm1250.blog.repository.TestRepository;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {

    private final TestRepository testRepository;

    @Autowired
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Transactional
    public TestEntity createTest() {
        TestEntity test = new TestEntity();
        return testRepository.save(test); // 데이터베이스에 엔티티를 저장합니다.
    }
}

