package com.crimsonlogic.arilinemanangmentsystem.repository;

import com.crimsonlogic.arilinemanangmentsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    /**
     * Action for findByEmailAndDeletedFalse.
     * @param email input parameter
     * @return Optional<User> output
     */
    Optional<User> findByEmailAndDeletedFalse(String email);
}
