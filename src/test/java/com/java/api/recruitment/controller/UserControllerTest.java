package com.java.api.recruitment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.java.api.recruitment.dto.ApiErrorDTO;
import com.java.api.recruitment.dto.UserDTO;
import com.java.api.recruitment.service.UserService;
import com.java.api.recruitment.utils.exception.CustomExceptionHandler;
import com.java.api.recruitment.utils.exception.exceptions.GitHubServerException;
import com.java.api.recruitment.utils.exception.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Unit test for UserController class
 *
 * @author Alicja Gratkowska
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserControllerTest {

    MockMvc mockMVC;

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    JacksonTester<UserDTO> jsonUserDTO;
    JacksonTester<ApiErrorDTO> jsonApiError;

    @BeforeEach
    public void setup() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        JacksonTester.initFields(this, objectMapper);
        mockMVC = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    public void testGetUSerByLogin_WhenUserIsFound() throws Exception {
        // given
        final String login = "testUser";
        final UserDTO userDTO = new UserDTO(1L, "TestUser", "Test User", "USER",
                "www.test-avatar-url.com", LocalDateTime.now(), 0.53f);

        // when
        given(userService.getUSerByLogin(login)).willReturn(userDTO);

        MockHttpServletResponse response = mockMVC.perform(
                        get("http://localhost:8080/users/" + login)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();


        //then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(jsonUserDTO.write(userDTO).getJson(), response.getContentAsString());
    }

    @Test
    public void testGetUSerByLogin_WhenUserIsNotFound() throws Exception {
        // given
        final String login = "testUser";
        final ApiErrorDTO apiErrorDTO = new ApiErrorDTO("User with login [testUser] does not exists.");

        // when
        given(userService.getUSerByLogin(login)).willThrow(new UserNotFoundException(login));

        MockHttpServletResponse response = mockMVC.perform(
                        get("http://localhost:8080/users/" + login)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals(jsonApiError.write(apiErrorDTO).getJson(), response.getContentAsString());
    }

    @Test
    public void testGetUSerByLogin_WhenGitHubServerError() throws Exception {
        // given
        final String login = "testUser";
        final ApiErrorDTO apiErrorDTO = new ApiErrorDTO("Error Message");

        // when
        given(userService.getUSerByLogin(login)).willThrow(new GitHubServerException(HttpStatusCode.valueOf(500),
               apiErrorDTO.errorMessage()));

        MockHttpServletResponse response = mockMVC.perform(
                        get("http://localhost:8080/users/" + login)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals(jsonApiError.write(apiErrorDTO).getJson(), response.getContentAsString());
    }
}
