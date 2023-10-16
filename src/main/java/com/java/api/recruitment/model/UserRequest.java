package com.java.api.recruitment.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class for USER_REQUEST table
 * Used to store number of requests for users/{login} endpoint
 *
 * @author Alicja Gratkowska
 */
@Entity
@Table(name = "USER_REQUEST")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    @Builder.Default
    private Integer counter = 1;

    public void increaseCounter() {
        this.counter++;
    }
}
