package com.kakaopay.flex.flex;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.flex.flex.controller.KakaopayFlexController;
import com.kakaopay.flex.flex.dto.FlexRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before; ;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class FlexApplicationTests {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    public static final String ROOMID="X-ROOM-ID";
    public static final String USERID="X-USER-ID";


    @Before
    public void setup() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(KakaopayFlexController.class).build();
    }

    @Test
    void insertFlexApiTest() throws Exception{
        FlexRequestDto requestDto = new FlexRequestDto();

        requestDto.setMoney(100);
        requestDto.setPeople(3);
        mockMvc.perform(
                post("/kakaopay/payFlex").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(ROOMID, "123")
                        .header(USERID, "tester1").content(objectMapper.writeValueAsString(requestDto))
        ).andDo(print());
    }


    @Test
    void updateFlexApiTest() throws Exception{

        mockMvc.perform(
                post("/kakaopay/payFlex/UPs").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(ROOMID, "1233")
                        .header(USERID, "tester2")
        ).andDo(print());
    }


    @Test
    void findFlexApiTest() throws Exception{

        mockMvc.perform(
                get("/kakaopay/payFlex/UPs").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(ROOMID, "123")
                        .header(USERID, "tester1")
        ).andDo(print());
    }
}
