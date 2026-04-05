package org.me.pvh_group_02_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.me.pvh_group_02_spring_mini_project.exception.BadRequestException;
import org.me.pvh_group_02_spring_mini_project.exception.CreateHabitException;
import org.me.pvh_group_02_spring_mini_project.exception.NotFoundException;
import org.me.pvh_group_02_spring_mini_project.exception.ResourceNotFoundException;
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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public List<Habit> getAllHabits(int page, int size) {
        Map<String, String> errors = new LinkedHashMap<>();

        if (page < 1) {
            errors.put("page", "must be greater than 0");
        }

        if (size < 1) {
            errors.put("size", "must be greater than 0");
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }

        int offset = size * (page - 1);
        return habitRepository.getAllHabits(offset, size);
    }

    @Override
    public List<Habit> getHabitById(UUID habitId) {
        List<Habit> habits = habitRepository.getHabitById(habitId);

        if (habits == null || habits.isEmpty()) {
            throw new ResourceNotFoundException("Habit with ID " + habitId + " not found");
        }

        return habits;
    }

    @Override
    public Habit createNewHabit(HabitRequest request) {
        AppUser user = getCurrentUser();

        boolean exists = habitRepository.existsByTitleAndDescription(
                request.getTitle(),
                request.getDescription(),
                user.getAppUserId()
        );

        if (exists) {
            Map<String, String> errors = new LinkedHashMap<>();

            errors.put("habit", "Habit with same title and description already exists");

            throw new CreateHabitException("Habit already exists", errors);
        }

        return habitRepository.createNewHabit(request, user.getAppUserId());
    }

    @Override
    public Habit updateHabitById(UUID habitId, HabitRequest request) {
        AppUser user = getCurrentUser();

        Habit existing = habitRepository.findByIdAndUserId(habitId, user.getAppUserId());

        if (existing == null) {
            throw new ResourceNotFoundException("Habit with ID " + habitId + " not found");
        }

        boolean exists = habitRepository.existsByTitleAndDescription(
                request.getTitle(),
                request.getDescription(),
                user.getAppUserId()
        );

        if (exists && !existing.getHabitId().equals(habitId)) {
            Map<String, String> errors = new LinkedHashMap<>();

            errors.put("habit", "Habit with same title and description already exists");

            throw new CreateHabitException("Duplicate habit", errors);
        }

        return habitRepository.updateHabitById(habitId, user.getAppUserId(), request);
    }

    @Override
    public void deleteHabitById(UUID habitId) {
        AppUser user = getCurrentUser();

        int deleted = habitRepository.deleteHabitById(habitId, user.getAppUserId());

        if (deleted == 0) {
            throw new ResourceNotFoundException("Habit with ID " + habitId + " not found");
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
