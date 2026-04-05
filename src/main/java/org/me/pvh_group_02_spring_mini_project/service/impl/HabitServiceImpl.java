package org.me.pvh_group_02_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.me.pvh_group_02_spring_mini_project.exception.NotFoundException;
import org.me.pvh_group_02_spring_mini_project.model.entity.AppUser;
import org.me.pvh_group_02_spring_mini_project.model.entity.Habit;
import org.me.pvh_group_02_spring_mini_project.model.request.HabitRequest;
import org.me.pvh_group_02_spring_mini_project.repository.AppUserRepository;
import org.me.pvh_group_02_spring_mini_project.repository.HabitRepository;
import org.me.pvh_group_02_spring_mini_project.service.HabitService;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public List<Habit> getAllHabits(int page, int size) {
        int offset = size * (page - 1);

        return habitRepository.getAllHabits(offset, size);
    }

    @Override
    public List<Habit> getHabitById(UUID habitId) {
        return habitRepository.getHabitById(habitId);
    }

    @Override
    public Habit createNewHabit(HabitRequest request) {
        AppUser user = getCurrentUser();
        return habitRepository.createNewHabit(request, user.getAppUserId());
    }

    @Override
    public Habit updateHabitById(UUID habitId, HabitRequest request) {
        AppUser user = getCurrentUser();
        Habit updatedHabit = habitRepository.updateHabitById(habitId, user.getAppUserId(), request);
        if (updatedHabit == null) {
            throw new NotFoundException("Habit not found");
        }

        return updatedHabit;
    }

    @Override
    public void deleteHabitById(UUID habitId) {
        AppUser user = getCurrentUser();
        int deletedRows = habitRepository.deleteHabitById(habitId, user.getAppUserId());
        if (deletedRows == 0) {
            throw new NotFoundException("Habit not found");
        }
    }

    private AppUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || "anonymousUser".equals(authentication.getName())) {
            throw new AuthenticationCredentialsNotFoundException("Authentication required");
        }

        AppUser user = appUserRepository.getUserByEmail(authentication.getName());
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }
}
