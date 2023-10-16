package com.java.api.recruitment.repository;

import com.java.api.recruitment.model.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository class to manage USER_REQUEST table
 *
 * @author Alicja Gratkowska
 */
@Repository
public interface UserRequestRepository extends JpaRepository<UserRequest, String> {

    Optional<UserRequest> findByLogin(final String login);
}
