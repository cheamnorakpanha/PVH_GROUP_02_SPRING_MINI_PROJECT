package org.me.pvh_group_02_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.me.pvh_group_02_spring_mini_project.model.entity.Achievement;
import org.me.pvh_group_02_spring_mini_project.repository.AchievementRepository;
import org.me.pvh_group_02_spring_mini_project.service.AchievementService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {
    private final AchievementRepository achievementRepository;

    @Override
    public List<Achievement> getAllAchievement(Integer page, Integer size) {
        Integer offset = size * (page -1 );
        return achievementRepository.getAllAchievement(offset,size);
    }

    @Override
    public List<Achievement> getAchievementByAppUserId(UUID appUserId, Integer page, Integer size) {
        Integer offset = size * (page -1 );
        return achievementRepository.findByAppUserId(appUserId,  offset,size);
    }
}
