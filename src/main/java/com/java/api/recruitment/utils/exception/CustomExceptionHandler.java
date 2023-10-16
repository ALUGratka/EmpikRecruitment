package com.java.api.recruitment.utils.exception;

import com.java.api.recruitment.dto.ApiErrorDTO;
import com.java.api.recruitment.utils.exception.exceptions.GitHubServerException;
import com.java.api.recruitment.utils.exception.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Custom Exception Handler for API
 *
 * @author Alicja Gratkowska
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = UserNotFoundException.class)
    public static ResponseEntity<ApiErrorDTO> handleUserNotFoundException(final UserNotFoundException userNotFoundException) {
        final ApiErrorDTO errorMessage = new ApiErrorDTO(userNotFoundException.getMessage());
        LOGGER.error(errorMessage.errorMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(value = GitHubServerException.class)
    public static ResponseEntity<ApiErrorDTO> handleGitHubServerException(final GitHubServerException gitHubServerException) {
        final ApiErrorDTO errorMessage = new ApiErrorDTO(gitHubServerException.getMessage());
        final HttpStatus httpStatus = HttpStatus.valueOf(gitHubServerException.getHttpStatusCode().value());
        LOGGER.error(errorMessage.errorMessage());
        return ResponseEntity.status(httpStatus).body(errorMessage);
    }
}
