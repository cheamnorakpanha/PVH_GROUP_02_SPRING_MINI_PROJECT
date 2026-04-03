package org.me.pvh_group_02_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.me.pvh_group_02_spring_mini_project.model.entity.AppUser;
import org.me.pvh_group_02_spring_mini_project.model.request.EditUserProfileRequest;
import org.me.pvh_group_02_spring_mini_project.model.response.AppUserResponse;

@Mapper
public interface ProfileRepository {

    @Results(id = "profileUserMapper", value = {
            @Result(property = "appUserId", column = "app_user_id"),
            @Result(property = "userName", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "level",          column = "level"),
            @Result(property = "xp",             column = "xp"),
            @Result(property = "profileImageUrl",column = "profile_image"),
            @Result(property = "isVerified",     column = "is_verified"),
            @Result(property = "createdAt",      column = "created_at")
    })

    @Select("""
    UPDATE app_users set username = #{req.userName}, profile_image = #{req.profileImageUrl} WHERE email = #{email} RETURNING *
    """)
    AppUserResponse updateUserProfile(String email, @Param("req") EditUserProfileRequest editRequest);

    @Delete("""
    DELETE FROM app_users WHERE email = #{email}
    """)
    void deleteUserProfile(String email);
}
