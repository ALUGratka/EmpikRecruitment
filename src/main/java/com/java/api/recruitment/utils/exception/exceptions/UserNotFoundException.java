package com.java.api.recruitment.utils.exception.exceptions;

/**
 * Custom exception for when user with login is not found on GitHub
 *
 * @author Alicja Gratkowska
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String login) {
        super(String.format("User with login [%s] does not exists.", login));
    }
}
