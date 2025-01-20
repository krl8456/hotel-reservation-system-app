package com.karol.hotelreservationsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karol.hotelreservationsystem.dto.SignInRequest;
import com.karol.hotelreservationsystem.filter.JwtAuthenticationFilter;
import com.karol.hotelreservationsystem.http.ApiResponse;
import com.karol.hotelreservationsystem.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenValidSignInRequest_whenLogin_thenReturnsIsOkStatus() throws Exception {
        SignInRequest request = new SignInRequest("example@gmail.com", "password");

        ApiResponse<String> response = ApiResponse.withData(HttpStatus.OK.value(), "User logged in", "token");
        when(authenticationService.authenticate(request)).thenReturn(objectMapper.writeValueAsString(response));

        ResultActions responseResult = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        responseResult.andExpect(status().isOk());
    }

    @Test
    public void givenValidSignInRequest_whenLogin_thenReturnsTokenInResponse() throws Exception {
        SignInRequest request = new SignInRequest("example@gmail.com", "password");
        ApiResponse<String> response = ApiResponse.withData(HttpStatus.OK.value(), "User logged in", "token");
        when(authenticationService.authenticate(request)).thenReturn(objectMapper.writeValueAsString(response));

        ResultActions responseResult = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        responseResult.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void givenInvalidSignInRequest_whenLogin_thenReturnsBadRequest() throws Exception {
        SignInRequest request = new SignInRequest("example", "password");

        ApiResponse<String> response = ApiResponse.withData(HttpStatus.OK.value(), "User logged in", "token");
        when(authenticationService.authenticate(request)).thenReturn(objectMapper.writeValueAsString(response));

        ResultActions responseResult = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        responseResult.andExpect(status().isBadRequest())
                .andExpect(content().string(notNullValue()));
    }
}