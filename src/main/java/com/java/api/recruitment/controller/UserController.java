package com.java.api.recruitment.controller;

import com.java.api.recruitment.dto.UserDTO;
import com.java.api.recruitment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller -
 *
 * @author Alicja Gratkowska
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get GitHub User by its login and increase quantity of requests for User.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GitHub User found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "GitHub User not found.",
                    content = @Content)})
    @GetMapping(path = "/{login}")
    public ResponseEntity<UserDTO> getUserByLogin(@PathVariable final String login) {
        final UserDTO user = userService.getUSerByLogin(login);
        return ResponseEntity.ok(user);
    }

}
