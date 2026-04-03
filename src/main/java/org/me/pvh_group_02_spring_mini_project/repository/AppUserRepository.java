package org.me.pvh_group_02_spring_mini_project.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.*;
import org.me.pvh_group_02_spring_mini_project.model.entity.AppUser;
import org.me.pvh_group_02_spring_mini_project.model.request.AppUserRequest;
import org.me.pvh_group_02_spring_mini_project.utils.TypeHandlerUUID;

import java.util.UUID;


@Mapper
public interface AppUserRepository {

    @Results (id = "appUserMapper", value = {
            @Result(property = "appUserId", column = "app_user_id", typeHandler = TypeHandlerUUID.class),
            @Result(property = "userName", column = "username"),
            @Result(property = "profileImageUrl", column = "profile_image"),
            @Result(property = "isVerified", column = "is_verified"),
            @Result(property = "createdAt", column = "created_at")
    })

    @Select("""
    SELECT * FROM app_users 
    WHERE email = #{email} 
    ORDER BY app_user_id DESC
    LIMIT 1
""")
    AppUser getUserByEmail(String email);

    @Select("""
    INSERT INTO app_users (app_user_id, username, email, password, profile_image, level, xp)
    VALUES (gen_random_uuid(), #{request.userName}, #{request.email}, #{request.password}, 
            #{request.profileImageUrl}, 1, 0)
    RETURNING *
""")
    @ResultMap("appUserMapper")
    AppUser register(@Param("request") AppUserRequest request);

    @ResultMap("appUserMapper")
    @Select("""
        SELECT * FROM app_users WHERE app_user_id = #{appUserId}
    """)
    AppUser getUserById(UUID appUserId);


    @Select("""
        SELECT EXISTS(SELECT 1 FROM app_users WHERE email = #{email})
    """)
    boolean existsUserEmail(@NotBlank(message = "must be a well-formed email address") @NotNull String email);

    @Select("""
        SELECT EXISTS(SELECT 1 FROM app_users WHERE username = #{userName})
    """)
    boolean existsUserName(@NotBlank(message = "must not be blank") @NotNull String userName);

    @Update("""
        UPDATE app_users
                SET is_verified = true
                WHERE email = #{email}
    """)
    void verifyUser(String email);
}