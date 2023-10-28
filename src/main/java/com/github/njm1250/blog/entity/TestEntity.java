package com.github.njm1250.blog.entity;

import javax.persistence.*;

@Entity // JPA가 관리하는 엔티티임을 나타냅니다.
@Table(name="users")
public class TestEntity {

    @Id // 이 필드가 테이블의 기본 키임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id; // 이 필드는 test 테이블의 iduser 컬럼에 매핑됩니다.

    // 추가 필드는 해당 테이블의 다른 컬럼을 나타냅니다.

    // 기본 생성자는 JPA가 필요로 합니다.
    public TestEntity() {
    }

    // getters and setters
    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long iduser) {
        this.user_id = iduser;
    }

    // 추가 필드에 대한 getters 및 setters...

}

