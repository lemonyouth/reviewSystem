package com.review.review_system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.review.review_system.DTO.LoginRequest;
import com.review.review_system.DTO.RegisterRequest;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegister() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("test1@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setUsername("testuser1");

        String json = objectMapper.writeValueAsString(registerRequest);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isOk());
    }

//    @Test
//    public void testRegister2() throws Exception {
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setEmail("test1@example.com");
//        registerRequest.setPassword("password123");
//        registerRequest.setUsername("testuser2");
//
//        String json = objectMapper.writeValueAsString(registerRequest);
//
//        mockMvc.perform(post("/api/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isConflict())
//                .andExpect(content().string("Email already exists"));
//
//    }
//    @Test
//    public void testRegister3() throws Exception {
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setEmail("test3@example.com");
//        registerRequest.setPassword("password123");
//        registerRequest.setUsername("testuser3");
//
//        String json = objectMapper.writeValueAsString(registerRequest);
//
//        mockMvc.perform(post("/api/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk());
//
//    }
//
//    @Test
//    public void testLogin1byUserName() throws Exception {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("testuser1");
//        loginRequest.setPassword("password123");
////        loginRequest.setEmail("test1@example.com");
//        String json = objectMapper.writeValueAsString(loginRequest);
//        mockMvc.perform((post("/api/auth/login").
//                contentType(MediaType.APPLICATION_JSON))
//                .content(json))
//                .andExpect(status().isOk());
//    }
//    @Test
//    public void testLoginByEmail() throws Exception {
//        LoginRequest loginRequest = new LoginRequest();
//
//        loginRequest.setPassword("password123");
//        loginRequest.setEmail("test1@example.com");
//        String json = objectMapper.writeValueAsString(loginRequest);
//        mockMvc.perform((post("/api/auth/login").
//                        contentType(MediaType.APPLICATION_JSON))
//                        .content(json))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testLogin2() throws Exception {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("testuser4");
//        loginRequest.setPassword("password123");
//        loginRequest.setEmail("test1@example.com");
//        String json = objectMapper.writeValueAsString(loginRequest);
//        mockMvc.perform((post("/api/auth/login").
//                        contentType(MediaType.APPLICATION_JSON))
//                        .content(json))
//                .andExpect(status().isOk());
//    }

}
