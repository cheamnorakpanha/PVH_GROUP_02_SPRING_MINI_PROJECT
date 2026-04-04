package org.me.pvh_group_02_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.me.pvh_group_02_spring_mini_project.model.entity.HabitLog;

import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitLogRepository {

    @Insert("INSERT INTO habit_logs (habit_log_id, log_date, status, xp_earned, habit_id) " +
            "VALUES (#{habitLogId}, #{logDate}, #{status}, #{xpEarned}, #{habitId})")
    void insertHabitLog(HabitLog habitLog);

    @Select("SELECT * FROM habit_logs WHERE habit_id = #{habitId} ORDER BY log_date DESC LIMIT #{size} OFFSET #{offset}")
    List<HabitLog> findByHabitId(@Param("habitId") UUID habitId,
                                 @Param("size") Integer size,
                                 @Param("offset") Integer offset);

    @Select("SELECT COUNT(*) FROM habit_logs hl JOIN habits h ON hl.habit_id = h.habit_id WHERE h.app_user_id = #{appUserId}")
    int countLogsByUserId(@Param("appUserId") UUID appUserId);
}
