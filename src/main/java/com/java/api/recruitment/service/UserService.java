package com.java.api.recruitment.service;

import com.java.api.recruitment.api.GitHubWebClientConfig;
import com.java.api.recruitment.dto.GitHubUserDTO;
import com.java.api.recruitment.dto.UserDTO;
import com.java.api.recruitment.mapper.UserMapper;
import com.java.api.recruitment.utils.exception.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Service class to call GitHub API and get user for login.
 *
 * @author Alicja Gratkowska
 */
@Service
public class UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final static String GITHUB_USERS_URL = "/users/";

    private final UserRequestService userRequestService;

    private final WebClient webClient;

    @Autowired
    public UserService(final UserRequestService userRequestService) {
        this.userRequestService = userRequestService;
        this.webClient = GitHubWebClientConfig.getInstance().build();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDTO getUSerByLogin(final String login) {
        final GitHubUserDTO gitHubUserDTO = webClient
                .get()
                .uri(String.format("%s%s", GITHUB_USERS_URL, login))
                .retrieve()
                .onStatus(httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        error -> Mono.error(new UserNotFoundException(login)))
                .bodyToMono(GitHubUserDTO.class)
                .block();

        LOGGER.info(String.format("User with login [%s] successfully retrieve from GitHub server",
                gitHubUserDTO.login()));

        userRequestService.increaseUserRequestCounter(login);

        return UserMapper.INSTANCE.mapGitHubUserDTOTOUserDTO(gitHubUserDTO);
    }


}
