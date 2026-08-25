package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Repository
public interface UserMapper extends org.springframework.data.repository.Repository<User, String> {

    @Query("SELECT u FROM User u WHERE u.email = ?1 AND u.deleted = false")
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id = ?1 AND u.deleted = false")
    User findById(String id);

    User save(User user);

    default int insertUser(User user) {
        save(user);
        return 1;
    }

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.lastLoginAt = ?2 WHERE u.id = ?1")
    int updateLastLogin(String id, java.time.LocalDateTime lastLoginAt);
}