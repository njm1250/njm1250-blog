package com.github.njm1250.blog.entity;

import javax.persistence.*;

@Entity // JPA가 관리하는 엔티티임을 나타냅니다.
@Table(name="user")
public class TestEntity {

    @Id // 이 필드가 테이블의 기본 키임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본 키가 자동 생성됨을 나타냅니다.
    private Long iduser; // 이 필드는 test 테이블의 iduser 컬럼에 매핑됩니다.

    // 추가 필드는 해당 테이블의 다른 컬럼을 나타냅니다.

    // 기본 생성자는 JPA가 필요로 합니다.
    public TestEntity() {
    }

    // getters and setters
    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }

    // 추가 필드에 대한 getters 및 setters...

}

