package com.java.api.recruitment.mapper;

import com.java.api.recruitment.dto.GitHubUserDTO;
import com.java.api.recruitment.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper between GitHub User DTO and Internal User DTO
 *
 * @author Alicja Gratkowska
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "calculations", expression = "java(calculate(gitHubUserDTO.followers(), gitHubUserDTO" +
            ".publicRepos()))")
    UserDTO mapGitHubUserDTOTOUserDTO(final GitHubUserDTO gitHubUserDTO);

    default Float calculate(final Integer followers, final Integer publicRepos) {
        if (followers == 0 || publicRepos == 0) return 0f;
        return 6f / (float) followers * (2f + (float) publicRepos);
    }
}
