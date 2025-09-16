package com.ceos22.spring_boot;

import static org.hamcrest.Matchers.equalTo; // 테스트 결과값 비교
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content; // HTTP 응답 body 검증
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // HTTP 응답 상태 검증

import org.junit.jupiter.api.DisplayName; // 한글 등으로 알아보기 쉬운 이름을 붙임
import org.junit.jupiter.api.Test; // 테스트 케이스임


// 테스트 환경 설정
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; //API 테스트를 위한 가짜 웹 환경
import org.springframework.boot.test.context.SpringBootTest; // 실제 애플리케이션처럼 모든 Bean을 로드하여 통합 테스트를 진행할 수 있게함
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc; //MockMvc: 가짜 웹 요청을 보내는 역할을 하는 객체
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("DisplayName : 테스트 이름을 설정할 수 있습니다")
    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().string(equalTo("Greetings from Spring Boot!")));
    }
}
