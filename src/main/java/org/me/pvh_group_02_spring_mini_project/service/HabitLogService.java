package org.me.pvh_group_02_spring_mini_project.service;

import org.me.pvh_group_02_spring_mini_project.model.entity.HabitLog;

import java.util.List;
import java.util.UUID;

public interface HabitLogService {
    HabitLog createHabitLog(UUID habitId, String status);
    List<HabitLog> getAllHabitLogByHabitId(UUID habitId, Integer page, Integer size);
}
