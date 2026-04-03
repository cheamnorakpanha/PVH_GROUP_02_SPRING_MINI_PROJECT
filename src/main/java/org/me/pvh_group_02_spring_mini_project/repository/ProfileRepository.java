package org.me.pvh_group_02_spring_mini_project.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.me.pvh_group_02_spring_mini_project.model.entity.AppUser;
import org.me.pvh_group_02_spring_mini_project.model.response.UserProfileResponse;

@Mapper
public interface ProfileRepository {
    @Results(id = "profileUserMapper", value = {
            @Result(property = "userId", column = "app_user_id"),
            @Result(property = "userName", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password",       column = "password"),
            @Result(property = "level",          column = "level"),
            @Result(property = "xp",             column = "xp"),
            @Result(property = "profileImageUrl",column = "profile_image_url"),
            @Result(property = "isVerified",     column = "is_verified"),
            @Result(property = "createdAt",      column = "created_at")
    })
    @Select("""
    SELECT * FROM app_users WHERE email = #{email}
    """)
    AppUser findUserByEmail(String email);
}
