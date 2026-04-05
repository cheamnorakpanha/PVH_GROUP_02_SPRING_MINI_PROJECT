package org.me.pvh_group_02_spring_mini_project.service;

import org.me.pvh_group_02_spring_mini_project.model.entity.Achievement;

import java.util.List;
import java.util.UUID;

public interface AchievementService {
    List<Achievement> getAllAchievement(Integer page, Integer size);

    List<Achievement> getAchievementByAppUserId(Integer page, Integer size);

}
