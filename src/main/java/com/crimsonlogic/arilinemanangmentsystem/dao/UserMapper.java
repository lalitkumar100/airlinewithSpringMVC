package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.type.EnumTypeHandler;
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE email = #{email} AND is_deleted = 0")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "dateOfBirth", column = "date_of_birth"),

            // Correct mapping for Gender enum
            @Result(property = "gender", column = "gender", javaType = com.crimsonlogic.arilinemanangmentsystem.enumrator.Gender.class, typeHandler = EnumTypeHandler.class),

            @Result(property = "email", column = "email"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "password", column = "password_hash"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at"),
            @Result(property = "deleted", column = "is_deleted"),

            // Correct mapping for Role enum
            @Result(property = "role", column = "role", javaType = com.crimsonlogic.arilinemanangmentsystem.enumrator.Role.class, typeHandler = EnumTypeHandler.class)
    })
    User findByEmail(String email);
}