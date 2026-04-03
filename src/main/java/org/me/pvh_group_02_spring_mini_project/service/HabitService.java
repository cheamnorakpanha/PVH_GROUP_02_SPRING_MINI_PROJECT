package org.me.pvh_group_02_spring_mini_project.service;

import org.me.pvh_group_02_spring_mini_project.model.entity.Habit;
import org.me.pvh_group_02_spring_mini_project.model.request.HabitRequest;

import java.util.List;
import java.util.UUID;

public interface HabitService {

    List<Habit> getAllHabits(int page, int size);

    List<Habit> getHabitById(UUID habitId);

    Habit createNewHabit(HabitRequest request);

    Habit updateHabitById(UUID habitId, HabitRequest request);

    void deleteHabitById(UUID habitId);
}
