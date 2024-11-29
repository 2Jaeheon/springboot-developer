package me.jaeheon.springbootdeveloper;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//리포지터리는 엔티티에 있는 데이터들을 조회하거나 저장, 변경, 삭제를 할 때 사용하는 인터페이스로,
//스프링 데이터 JPA에서 제공하는 인터페이스인 JpaRepository 클래스를 상속받아 간단하게 구현 가능
@Repository
//상속받을 때 엔티티와 엔티티의 기본키 타입을 인수로 넣어줌.
public interface MemberRepository extends JpaRepository<Member, Long> {
    //이게 쿼리 메서드임.
    // JPA가 정해준 메서드 이름 규칙을 따르면 쿼리문을 구현하지 않아도 메서드처럼 사용가능
    Optional<Member> findByName(String name);

    // 쿼리가 복잡하거나 성능이 중요해서 SQL 쿼리를 직접 사용해야하는경우
    // @Query("select m from Member m where m.name = ?1")
    // Optional<Member> findByNameQuery(String name)
}


// 전체 조회 -> findAll()
// 아이디로 조회 -> findById() 메서드 사용
// 특정 컬럼으로 조회 -> 쿼리 메서드 명명 규칙에 맞게 정의 후 사용


