package com.java.api.recruitment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.java.api.recruitment.dto.GitHubUserDTO;
import com.java.api.recruitment.dto.UserDTO;
import com.java.api.recruitment.utils.exception.exceptions.UserNotFoundException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRequestService userRequestService;

    private MockWebServer mockWebServer;

    @BeforeEach
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        String baseUrl = mockWebServer.url("/").toString();
        WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();
        ReflectionTestUtils.setField(userService, "webClient", webClient);
    }

    @AfterEach
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testGetUserByLogin_WhenUserOnGitHub() throws IOException {
        // given
        GitHubUserDTO mockedGitHubUserDTO = new GitHubUserDTO(1L, "TestUser", "Test User", "USER",
                100, "www.test-avatar-url.com", LocalDateTime.now(), 5);

        // when
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(toJson(mockedGitHubUserDTO))
                .addHeader("Content-Type", "application/json"));


        UserDTO userDTO = userService.getUSerByLogin("TestUser");

        // then
        assertEquals(1L, userDTO.id());
        assertEquals("TestUser", userDTO.login());
        assertEquals("Test User", userDTO.name());
        assertEquals("www.test-avatar-url.com", userDTO.avatarUrl());

        verify(userRequestService, times(1)).increaseUserRequestCounter("TestUser");
    }

    @Test
    public void testGetUserByLogin_WhenUserNotOnGitHub() throws IOException {
        // when
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .addHeader("Content-Type", "application/json"));


        // then
        assertThrows(UserNotFoundException.class, () -> userService.getUSerByLogin("TestUser"),
                "User with login [TestUser] does not exists");

        verify(userRequestService, never()).increaseUserRequestCounter("TestUser");
    }

    private String toJson(final GitHubUserDTO gitHubUserDTO) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.writeValueAsString(gitHubUserDTO);
    }
}
