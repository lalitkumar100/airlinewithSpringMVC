package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Repository
public interface UserMapper extends org.springframework.data.repository.Repository<User, String> {

    /**
     * Action for findByEmail.
     * @param email input parameter
     * @return User output
     */
    @Query("SELECT u FROM User u WHERE u.email = ?1 AND u.deleted = false")
    User findByEmail(String email);

    /**
     * Action for findById.
     * @param id input parameter
     * @return User output
     */
    @Query("SELECT u FROM User u WHERE u.id = ?1 AND u.deleted = false")
    User findById(String id);

    /**
     * Action for save.
     * @param user input parameter
     * @return User output
     */
    User save(User user);

    default int insertUser(User user) {
        save(user);
        return 1;
    }

    /**
     * Action for updateLastLogin.
     * @param id input parameter
     * @param lastLoginAt input parameter
     * @return int output
     */
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.lastLoginAt = ?2 WHERE u.id = ?1")
    int updateLastLogin(String id, java.time.LocalDateTime lastLoginAt);
}