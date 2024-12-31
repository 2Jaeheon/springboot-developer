package me.jaeheon.springbootdeveloper;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jaeheon.springbootdeveloper.QuizController.Code;
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

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    //Jackson 라이브러리에서 제공하는 클래스로 객체와 JSON 사이의 변환을 담당합니다.
    //즉, 객체 직렬화
    private ObjectMapper objectMapper;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @DisplayName("quiz(): GET /quiz?code=1 이면 응답코드는 201," + "응답 본문은 Created!를 리턴한다.")
    @Test
    public void getQuiz1() throws Exception {
        //given
        final String url = "/quiz";

        //when
        final ResultActions result = mockMvc.perform(get(url)
            .param("code", "1"));

        //then
        result
            .andExpect(status().isCreated())
            .andExpect(content().string("Created"));
    }

    @DisplayName("quiz(): GET /quiz?code=2 이면 응답코드는 400"
        + "응답 본문은 Bad Request를 리턴한다.")
    @Test
    public void getQuiz2() throws Exception {
        //given
        final String url = "/quiz";

        //when
        ResultActions result = mockMvc.perform(get(url)
            .param("code", "2"));

        //then
        result
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Bad Request"));
    }


    //문제 2
    @DisplayName("quiz(): POST / quiz?code=1 이면 응답코드는 403,"
        + "응답 본문은 Forbidden!를 리턴한다.")
    @Test
    public void postQuiz1() throws Exception {
        //given
        final String url = "/quiz";

        //when
        final ResultActions result = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new Code(1)))
        );

        //then
        result
            .andExpect(status().isForbidden())
            .andExpect(content().string("Forbidden"));

    }

    @DisplayName("quiz(): POST / quiz?code=2 이면 응답코드는 200,"
        + "응답 본문은 OK를 리턴한다.")
    @Test
    public void postQuiz13() throws Exception {
        //given
        final String url = "/quiz";

        //when
        final ResultActions result = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new Code(2)))
        );

        //then
        result
            .andExpect(status().isOk())
            .andExpect(content().string("OK"));
    }
}