package com.socialnetwork.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialnetwork.dto.SignUpDTO;
import com.socialnetwork.dto.UserDTO;
import com.socialnetwork.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AuthenticationControllerTest {
    MockMvc mockMvc;

    @Autowired
    AuthenticationController authenticationController;

    @MockBean
    UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setuo() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    public void testSignUp() throws Exception {
        //given
        SignUpDTO signUpDTO = SignUpDTO.builder()
                .firstName("santosh")
                .lastName("sharma")
                .login("santosh")
                .password("pass".toCharArray())
                .build();
        //when
        when(userService.signUp(signUpDTO))
                .thenReturn(UserDTO.builder()
                        .id(1L)
                        .firstName("santosh")
                        .lastName("sharma")
                        .token("token")
                        .build());
        //then
        mockMvc.perform(post("/v1/signUp")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(signUpDTO)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.token", is("token")));

    }
}