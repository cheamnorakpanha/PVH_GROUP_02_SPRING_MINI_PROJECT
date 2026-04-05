package org.me.pvh_group_02_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.me.pvh_group_02_spring_mini_project.model.entity.Achievement;
import org.me.pvh_group_02_spring_mini_project.utils.TypeHandlerUUID;

import java.util.List;
import java.util.UUID;

@Mapper
public interface AchievementRepository {
    @Results(id = "achievementMapper", value = {
            @Result(property = "achievementId", column = "achievement_id",typeHandler = TypeHandlerUUID.class),
            @Result(property = "xpRequired", column = "xp_required")
    })
    @Select("""
Select * from achievements  
OFFSET #{offset} LIMIT #{size}
""")
    List<Achievement> getAllAchievement(@Param("offset") Integer offset, @Param("size") Integer size);


    @Select("""
        SELECT a.*
        FROM achievements a
        INNER JOIN app_user_achievements aua
            ON a.achievement_id = aua.achievement_id
        WHERE aua.app_user_id = #{appUserId}
        OFFSET #{offset} LIMIT #{size}
        """)
    @ResultMap("achievementMapper")
    List<Achievement> findByAppUserId(UUID appUserId, Integer offset, Integer size);
}
