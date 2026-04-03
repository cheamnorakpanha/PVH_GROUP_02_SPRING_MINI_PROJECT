package org.me.pvh_group_02_spring_mini_project.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.me.pvh_group_02_spring_mini_project.model.entity.AppUser;
import org.me.pvh_group_02_spring_mini_project.utils.TypeHandlerUUID;


@Mapper
public interface AppUserRepository {

    @Results (id = "appUserMapper", value = {
            @Result(property = "userId", column = "app_user_id", typeHandler = TypeHandlerUUID.class),
            @Result(property = "userName", column = "username"),
            @Result(property = "profileImageUrl", column = "profile_image")
    })

    @Select("""
    SELECT * FROM app_users 
    WHERE email = #{email} 
    ORDER BY app_user_id DESC
    LIMIT 1
""")
    AppUser getUserByEmail(String email);
}
