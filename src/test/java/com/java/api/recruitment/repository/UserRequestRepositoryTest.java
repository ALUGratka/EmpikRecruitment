package com.java.api.recruitment.repository;

import com.java.api.recruitment.model.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for UserRequestRepository class
 *
 * @author Alicja Gratkowska
 */
@DataJpaTest
@ExtendWith(SpringExtension.class)
public class UserRequestRepositoryTest {

    @Autowired
    UserRequestRepository userRequestRepository;

    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    public void setup() {
        final UserRequest userRequest = UserRequest.builder().login("testUser").counter(4).build();
        entityManager.persistAndFlush(userRequest);
    }

    @Test
    public void testFindByLogin_WhenUserRequestFound() {
        // given + when
        Optional<UserRequest> userRequestOptional = userRequestRepository.findByLogin("testUser");

        // then
        assertThat(userRequestOptional).isNotEmpty();
        assertEquals(userRequestOptional.get().getId(), 1);
        assertEquals(userRequestOptional.get().getLogin(), "testUser");
        assertEquals(userRequestOptional.get().getCounter(), 4);
    }

    @Test
    public void testFindByLogin_WhenUserRequestNotFound() {
        // given + when
        Optional<UserRequest> userRequestOptional = userRequestRepository.findByLogin("unknownTestUser");

        // then
        assertThat(userRequestOptional).isEmpty();
    }
}
