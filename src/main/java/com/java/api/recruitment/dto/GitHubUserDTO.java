package com.java.api.recruitment.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * DTO clas for GitHub User
 *
 * @author Alicja Gratkowska
 */
public record GitHubUserDTO(Long id, String login, String name, String type, Integer followers,
                            @JsonProperty("avatar_url") String avatarUrl,
                            @JsonProperty("created_at") LocalDateTime createdAt,
                            @JsonProperty("public_repos") Integer publicRepos) {
}
