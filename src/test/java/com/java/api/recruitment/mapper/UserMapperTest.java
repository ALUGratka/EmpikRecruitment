package com.java.api.recruitment.mapper;

import com.java.api.recruitment.dto.GitHubUserDTO;
import com.java.api.recruitment.dto.UserDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for UserMapper class
 *
 * @author Alicja Gratkowska
 */
public class UserMapperTest {

    @Test
    public void testMapGitHubUserToUserDTO_OnSuccess() {
        // given
        final GitHubUserDTO gitHubUserDTO = new GitHubUserDTO(1L, "TestUser", "Test User", "USER",
                100, "www.avatar.com", LocalDateTime.now(), 3);

        // when
        final UserDTO userDTO = UserMapper.INSTANCE.mapGitHubUserDTOTOUserDTO(gitHubUserDTO);

        // then
        assertEquals(userDTO.id(), gitHubUserDTO.id());
        assertEquals(userDTO.login(), gitHubUserDTO.login());
        assertEquals(userDTO.name(), gitHubUserDTO.name());
        assertEquals(userDTO.avatarUrl(), gitHubUserDTO.avatarUrl());
        assertEquals(userDTO.createdAt(), gitHubUserDTO.createdAt());
        assertEquals(userDTO.calculations(), calculate(gitHubUserDTO));
    }

    @Test
    public void testMapGitHubUserToUserDTO_OnSuccessWhenFollowersEqualsZero() {
        // given
        final GitHubUserDTO gitHubUserDTO = new GitHubUserDTO(1L, "TestUser", "Test User", "USER",
                100, "www.avatar.com", LocalDateTime.now(), 0);

        // when
        final UserDTO userDTO = UserMapper.INSTANCE.mapGitHubUserDTOTOUserDTO(gitHubUserDTO);

        // then
        assertEquals(userDTO.id(), gitHubUserDTO.id());
        assertEquals(userDTO.login(), gitHubUserDTO.login());
        assertEquals(userDTO.name(), gitHubUserDTO.name());
        assertEquals(userDTO.avatarUrl(), gitHubUserDTO.avatarUrl());
        assertEquals(userDTO.createdAt(), gitHubUserDTO.createdAt());
        assertEquals(userDTO.calculations(), calculate(gitHubUserDTO));
    }

    @Test
    public void testMapGitHubUserToUserDTO_OnSuccessWhenPublicReposEqualsZero() {
        // given
        final GitHubUserDTO gitHubUserDTO = new GitHubUserDTO(1L, "TestUser", "Test User", "USER",
                0, "www.avatar.com", LocalDateTime.now(), 3);

        // when
        final UserDTO userDTO = UserMapper.INSTANCE.mapGitHubUserDTOTOUserDTO(gitHubUserDTO);

        // then
        assertEquals(userDTO.id(), gitHubUserDTO.id());
        assertEquals(userDTO.login(), gitHubUserDTO.login());
        assertEquals(userDTO.name(), gitHubUserDTO.name());
        assertEquals(userDTO.avatarUrl(), gitHubUserDTO.avatarUrl());
        assertEquals(userDTO.createdAt(), gitHubUserDTO.createdAt());
        assertEquals(userDTO.calculations(), calculate(gitHubUserDTO));
    }

    private float calculate(final GitHubUserDTO gitHubUserDTO) {
        if(gitHubUserDTO.followers() == 0 || gitHubUserDTO.publicRepos() == 0) return 0f;
        return 6f / (float) gitHubUserDTO.followers() * (2f + (float) gitHubUserDTO.publicRepos());
    }
}
