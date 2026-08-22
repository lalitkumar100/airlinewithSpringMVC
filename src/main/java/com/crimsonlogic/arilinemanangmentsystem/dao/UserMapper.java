package com.crimsonlogic.arilinemanangmentsystem.dao;


import com.crimsonlogic.arilinemanangmentsystem.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.EnumTypeHandler;

@Mapper
public interface UserMapper {

    @Results(id = "UserResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "dateOfBirth", column = "date_of_birth"),
            @Result(property = "gender", column = "gender", javaType = com.crimsonlogic.arilinemanangmentsystem.enumrator.Gender.class, typeHandler = EnumTypeHandler.class),
            @Result(property = "email", column = "email"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "password", column = "password_hash"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at"),
            @Result(property = "deleted", column = "is_deleted"),
            @Result(property = "role", column = "role", javaType = com.crimsonlogic.arilinemanangmentsystem.enumrator.Role.class, typeHandler = EnumTypeHandler.class),
            @Result(property = "lastLoginAt", column = "last_login_at")
    })
    @Select("SELECT * FROM user WHERE email = #{email} AND is_deleted = 0")
    User findByEmail(String email);

    @ResultMap("UserResultMap")
    @Select("SELECT * FROM user WHERE id = #{id} AND is_deleted = 0")
    User findById(@Param("id") String id);

    @Insert("""
        INSERT INTO user (
            id, first_name, last_name, date_of_birth, gender, 
            email, phone_number, password_hash, created_at, 
            updated_at, is_deleted, role, last_login_at
        ) VALUES (
            #{id}, #{firstName}, #{lastName}, #{dateOfBirth}, 
            #{gender, typeHandler=org.apache.ibatis.type.EnumTypeHandler}, 
            #{email}, #{phoneNumber}, #{password}, #{createdAt}, 
            #{updatedAt}, #{deleted}, 
            #{role, typeHandler=org.apache.ibatis.type.EnumTypeHandler},
            #{lastLoginAt}
        )
    """)
    int insertUser(User user);

    @Update("UPDATE user SET last_login_at = #{lastLoginAt} WHERE id = #{id}")
    int updateLastLogin(@Param("id") String id, @Param("lastLoginAt") java.time.LocalDateTime lastLoginAt);
}