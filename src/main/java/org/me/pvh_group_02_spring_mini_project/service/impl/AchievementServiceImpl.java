package org.me.pvh_group_02_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.me.pvh_group_02_spring_mini_project.exception.NotFoundException;
import org.me.pvh_group_02_spring_mini_project.model.entity.Achievement;
import org.me.pvh_group_02_spring_mini_project.model.entity.AppUser;
import org.me.pvh_group_02_spring_mini_project.model.request.EditUserProfileRequest;
import org.me.pvh_group_02_spring_mini_project.model.response.AppUserResponse;
import org.me.pvh_group_02_spring_mini_project.repository.AchievementRepository;
import org.me.pvh_group_02_spring_mini_project.repository.AppUserRepository;
import org.me.pvh_group_02_spring_mini_project.service.AchievementService;
import org.me.pvh_group_02_spring_mini_project.utils.HandleCurrentUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {
    private final AchievementRepository achievementRepository;
    private final HandleCurrentUser handleCurrentUser;
    private final AppUserRepository appUserRepository;

    @Override
    public List<Achievement> getAllAchievement(Integer page, Integer size) {
        Integer offset = size * (page -1 );
        return achievementRepository.getAllAchievement(offset,size);
    }

    @Override
    public List<Achievement> getAchievementByAppUserId(Integer page, Integer size) {
        UUID currentUserId = handleCurrentUser.getUserIdOfCurrentUser();
        AppUser user = appUserRepository.getUserById(currentUserId);
        if (user == null) {
            throw new NotFoundException("User not found with id: " + currentUserId);
        }
        Integer offset = size * (page -1 );
        return achievementRepository.findByAppUserId(user.getAppUserId(),  offset,size);
    }
}
