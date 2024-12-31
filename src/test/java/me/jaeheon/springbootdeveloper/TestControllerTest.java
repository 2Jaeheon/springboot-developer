package me.jaeheon.springbootdeveloper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest // 테스트를 위한 어노테이션
@AutoConfigureMockMvc // MockMvc를 사용하기 위한 어노테이션
    // MockMvc는 서블릿 컨테이너를 띄우지 않고도 스프링 MVC(DispatcherServlet)가
    // 요청을 처리하는 방식으로 테스트를 진행할 수 있게 해준다.
class TestControllerTest {

    @Autowired
    protected MockMvc mockMvc; // MockMvc 주입

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    public void cleanUp() {
        memberRepository.deleteAll();
    }

    @DisplayName("getAllMembers 테스트")
    @Test
    public void getAllMembers() throws Exception {
        //given
        final String url = "/test";
        Member savedMember = memberRepository.save(new Member(1L, "홀길동"));

        //when
        //perform: 요청을 보내는 메서드
        final ResultActions result = mockMvc.perform(get(url)
            .accept(MediaType.APPLICATION_JSON));
            //accept는 요청을 보낼 때 무슨 타입으로 응답을 받을 것인지 설정하는 메서드

        //then
        result
            //응답을 검증 (status가 200인지 확인)
            .andExpect(status().isOk())
            //응답의 0번째 값이 DB에 저장된 값과 같은지 확인
            .andExpect(jsonPath("$[0].id").value(savedMember.getId()))
            .andExpect(jsonPath("$[0].name").value(savedMember.getName()));
    }
}