package org.me.pvh_group_02_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.me.pvh_group_02_spring_mini_project.model.entity.Habit;
import org.me.pvh_group_02_spring_mini_project.model.request.HabitRequest;
import org.me.pvh_group_02_spring_mini_project.utils.TypeHandlerUUID;

import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitRepository {

    @Results(id = "habitMapper", value = {
            @Result(property = "habitId", column = "habit_id", typeHandler = TypeHandlerUUID.class),
            @Result(property = "isActive", column = "is_active"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(
                    property = "appUserResponse",
                    column = "app_user_id",
                    one = @One(select = "org.me.pvh_group_02_spring_mini_project.repository.AppUserRepository.getUserResponseById")
            )
    })
    @Select("""
            SELECT * FROM habits LIMIT #{size} OFFSET #{offset}
            """)
    List<Habit> getAllHabits(@Param("offset") int offset, @Param("size") int size);

    @ResultMap("habitMapper")
    @Select("""
            SELECT * FROM habits WHERE habit_id = #{habitId}
            """)
    List<Habit> getHabitById(@Param("habitId") UUID habitId);

    @ResultMap("habitMapper")
    @Select("""
            INSERT INTO habits (habit_id, title, description, frequency, app_user_id)
            VALUES (gen_random_uuid(), #{req.title}, #{req.description}, #{req.frequency}, #{userId})
            RETURNING *
            """)
    Habit createNewHabit(@Param("req") HabitRequest request, @Param("userId") UUID userId);

    @ResultMap("habitMapper")
    @Select("""
            UPDATE habits
            SET title = #{req.title}, description = #{req.description}, frequency = #{req.frequency}
            WHERE habit_id = #{habitId} AND app_user_id = #{userId} RETURNING *
            """)
    Habit updateHabitById(@Param("habitId") UUID habitId, @Param("userId") UUID userId, @Param("req") HabitRequest request);

    @Delete("""
            DELETE FROM habits
            WHERE habit_id = #{habitId} AND app_user_id = #{userId}
            """)
    int deleteHabitById(@Param("habitId") UUID habitId, @Param("userId") UUID userId);
}
