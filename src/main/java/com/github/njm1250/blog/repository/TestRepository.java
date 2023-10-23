package com.github.njm1250.blog.repository;

import com.github.njm1250.blog.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 이 인터페이스가 데이터 액세스 레이어의 역할을 한다는 것을 나타냅니다.
public interface TestRepository extends JpaRepository<TestEntity, Long> {
    // JpaRepository의 첫 번째 매개변수는 엔티티 타입이고, 두 번째는 기본 키의 클래스 타입입니다.
}
