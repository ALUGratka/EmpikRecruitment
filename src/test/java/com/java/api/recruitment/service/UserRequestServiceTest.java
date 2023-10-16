package com.java.api.recruitment.service;

import com.java.api.recruitment.model.UserRequest;
import com.java.api.recruitment.repository.UserRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for UserRequestService class
 *
 * @author Alicja Gratkowska
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRequestServiceTest {

    @InjectMocks
    UserRequestService userRequestService;

    @Mock
    UserRequestRepository userRequestRepository;

    @Test
    public void testIncreaseUserRequestCounter_WhenUserRequestFound() {
        // given
        final String login = "TestUser";
        final UserRequest userRequest = UserRequest.builder().id(1L).login("testUser").counter(4).build();

        // when
        when(userRequestRepository.findByLogin(any())).thenReturn(Optional.of(userRequest));

        userRequestService.increaseUserRequestCounter(login);

        // then
        verify(userRequestRepository, times(1)).save(any());
    }

    @Test
    public void testIncreaseUserRequestCounter_WhenUserRequestNotFound() {
        // given
        final String login = "TestUser";

        // when
        when(userRequestRepository.findByLogin(any())).thenReturn(Optional.empty());

        userRequestService.increaseUserRequestCounter(login);

        // then
        verify(userRequestRepository, times(1)).save(any());
    }
}
