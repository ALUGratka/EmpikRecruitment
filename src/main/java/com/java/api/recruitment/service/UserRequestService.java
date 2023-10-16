package com.java.api.recruitment.service;

import com.java.api.recruitment.model.UserRequest;
import com.java.api.recruitment.repository.UserRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class to store and increase number of user requests
 *
 * @author Alicja Gratkowska
 */
@Service
public class UserRequestService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserRequestService.class);

    private final UserRequestRepository userRequestRepository;

    @Autowired
    public UserRequestService(final UserRequestRepository userRequestRepository) {
        this.userRequestRepository = userRequestRepository;
    }

    public void increaseUserRequestCounter(final String login) {
        Optional<UserRequest> userRequestOptional = userRequestRepository.findByLogin(login);
        final UserRequest userRequest;

        if (userRequestOptional.isEmpty()) {
            userRequest = UserRequest.builder().login(login).build();
        } else {
            userRequest = userRequestOptional.get();
            userRequest.increaseCounter();
        }
        userRequestRepository.save(userRequest);
        LOGGER.info(String.format("Request counter for User with login [%s] increased to [%d]",
                userRequest.getLogin(), userRequest.getCounter()));
    }
}
