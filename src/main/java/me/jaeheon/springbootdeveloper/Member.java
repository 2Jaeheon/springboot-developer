package me.jaeheon.springbootdeveloper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity //엔티티로 지정
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자
@AllArgsConstructor
public class Member {

    @Id //id 필드를 기본키로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //기본 키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id; // DB 테이블의 'id' 컬럼과 매칭

    @Column(name = "name", nullable = false)
    private String name; // DB 테이블의 'name' 컬럼과 매칭

    // 만약 이 메서드가 @Transactinoal 애너테이션이 포함된 메서드에서 호출되면
    // JPA는 변경 감지 (dirty checking)을 통해서 엔티티 필드값이 변경될 때 변경사항을 DB에 자동 반영
    // 만약 엔티티가 영속 상태일 때 필드값을 변경하고
    // 트렌젝션이 커밋되면 JPA는 변경 사항을 DB에 자동 적용
    public void changeName(String name) {
        this.name = name;
    }
}

// @Entity는 Member객체를 JPA가 관리하는 엔티티로 지정 == Member클래스와 실제 DB 테이블을 매핑
// Entity 속성 중에 name을 사용하면 name을 가진 테이블 이름과 매핑되고,
// 테이블 이름을 지정하지 않을시 클래스 이름과 같은 이름의 테이블과 매핑

// Protected 기본생성자 엔티티는 반드시 기본생성자가 있어야하며, 접근제어자는 public 또는 protected여야 함.
// public 보다는 protected 가 안전하므로 기본생성자를 설정
// GeneratedValue는 기본키의 생성 방식을 결정
/*
* AUTO: 선택한 DB dialect에 따라 방식을 자동으로 설정
* IDENTITY 기본키 생성을 DB에 위임
* SEQUENCE: 디비 시퀀스를 사용해서 기본키를 할당 -> 오라클
* TABLE: 키 생성 테이블 사용
* */
//@Column 애너테이션은 DB 컬럼과 필드를 매핑.
/*
* name: 필드와 매핑할 컬럼 이름. 설정하지 않으면 필드 이름으로 지정
* nullable: 컬럼의 null 허용 여부
* unique: 컬럼의 유일한 값 여부. 설정하지 않으면 false
* columnDefinition: 컬럼 정보 설정
* */