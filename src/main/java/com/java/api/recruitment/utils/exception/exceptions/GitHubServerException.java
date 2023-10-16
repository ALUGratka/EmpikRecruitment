package com.java.api.recruitment.utils.exception.exceptions;

import org.springframework.http.HttpStatusCode;

/**
 * Custom exception for GitHUb Server errors
 *
 * @author Alicja Gratkowska
 */
public class GitHubServerException extends RuntimeException {

    private final HttpStatusCode httpStatusCode;

    public GitHubServerException(HttpStatusCode statusCode, String errorMessage) {
        super(errorMessage);
        this.httpStatusCode = statusCode;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }
}
