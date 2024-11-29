package me.jaeheon.springbootdeveloper;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
//DataJpaTest 애너테이션은 테스트를 위한 설정을 제공하며 자동으로 DB에 대한 트랜젝션 관리 설정함.
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    public void cleanUp() {
        memberRepository.deleteAll();
    }

    //Sql 애너테이션을 사용하면 실행하기 전 SQL 스크립트 실행이 가능함.
    @Sql("/insert-members.sql")
    @Test
    void getAllMembers() {
        //when
        List<Member> members = memberRepository.findAll();

        //then
        assertThat(members.size()).isEqualTo(3);
    }

    @Sql("/insert-members.sql")
    @Test
    void getMemberById() {
        //when
        Member member = memberRepository.findById(2L).get();

        //then
        assertThat(member.getName()).isEqualTo("B");
    }

    @Sql("/insert-members.sql")
    @Test
    void getMemberByName() {
        //when
        Member member = memberRepository.findByName("C").get();
        //then
        assertThat(member.getId()).isEqualTo(3);
    }

    @Test
    void saveMember() {
        //given
        Member member = new Member(1L, "A");
        //when
        memberRepository.save(member);
        //then
        assertThat(memberRepository.findById(1L).get().getName()).isEqualTo("A");
    }

    @Test
    void saveMembers() {
        //given
        List<Member> members = List.of(new Member(2L, "B"), new Member(3L, "C"));

        //when
        memberRepository.saveAll(members);

        //then
        assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

    @Sql("/insert-members.sql")
    @Test
        //이거 잘 안 쓸텐데?? 어디써? => 테스트간의 격리를 보장하기 위해서 사용

    void deleteAll() {
        //when
        memberRepository.deleteAll();

        //then
        assertThat(memberRepository.findAll().size()).isZero();
    }

    //실제 서비스코드에서는 서비스메서드에 @Transactional을 붙여야함.
    //지금은 @DataJpaTest로 대신했으나 @Transactional 사용해야함.
    @Transactional
    @Sql("/insert-members.sql")
    @Test
    void update() {
        //given
        Member member = memberRepository.findById(2L).get();

        //when
        member.changeName("BC");

        //then
        assertThat(memberRepository.findById(2L).get().getName()).isEqualTo("BC");
    }
}

// 레코드 추가 save()
// 한꺼번에 여러 레코드 추가 saveAll()
// 아이디로 레코드 삭제 deleteById()
// 모든 레코드 삭제 deleteAll()