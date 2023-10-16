package com.java.api.recruitment.dto;

import java.time.LocalDateTime;

/**
 * Internal DTO class for User
 *
 * @author Alicja Gratkowska
 */
public record UserDTO(Long id, String login, String name, String type, String avatarUrl, LocalDateTime createdAt,
                      Float calculations) {
}
