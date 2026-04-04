package org.me.pvh_group_02_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.me.pvh_group_02_spring_mini_project.exception.BadRequestException;
import org.me.pvh_group_02_spring_mini_project.exception.NotFoundException;
import org.me.pvh_group_02_spring_mini_project.model.entity.AppUser;
import org.me.pvh_group_02_spring_mini_project.model.entity.Habit;
import org.me.pvh_group_02_spring_mini_project.model.entity.HabitLog;
import org.me.pvh_group_02_spring_mini_project.repository.AppUserRepository;
import org.me.pvh_group_02_spring_mini_project.repository.HabitLogRepository;
import org.me.pvh_group_02_spring_mini_project.repository.HabitRepository;
import org.me.pvh_group_02_spring_mini_project.service.HabitLogService;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitLogServiceImpl implements HabitLogService {

    private final HabitLogRepository habitLogRepository;
    private final HabitRepository habitRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public HabitLog createHabitLog(UUID habitId, String status) {
        AppUser user = getCurrentUser();
        Habit habit = getHabitByIdOrThrow(habitId);
        if (!habit.getAppUserId().equals(user.getAppUserId())) {
            throw new NotFoundException("Habit not found");
        }
        if (status == null || status.isBlank()) {
            throw new BadRequestException("Status is required");
        }
        String normalizedStatus = status.trim().toUpperCase();

        HabitLog log = new HabitLog();
        log.setHabitLogId(UUID.randomUUID());
        log.setHabitId(habitId);
        log.setLogDate(LocalDate.now());
        log.setStatus(normalizedStatus);
        log.setXpEarned("COMPLETED".equals(normalizedStatus) ? 10 : 0);

        habitLogRepository.insertHabitLog(log);

        // XP & Level logic
        int totalLogs = habitLogRepository.countLogsByUserId(user.getAppUserId());
        int newXp = user.getXp() + log.getXpEarned();
        int computedLevel = (totalLogs / 10) + 0;
        int newLevel = Math.max(user.getLevel(), computedLevel);

        appUserRepository.updateUserXpAndLevel(user.getAppUserId(), newXp, newLevel);

        return log;
    }

    @Override
    public List<HabitLog> getAllHabitLogByHabitId(UUID habitId, Integer page, Integer size) {
        if (page == null || size == null || page < 1 || size < 1) {
            throw new BadRequestException("Page and size must be positive");
        }
        AppUser user = getCurrentUser();
        Habit habit = getHabitByIdOrThrow(habitId);
        if (!habit.getAppUserId().equals(user.getAppUserId())) {
            throw new NotFoundException("Habit not found");
        }
        int offset = (page - 1) * size;
        return habitLogRepository.findByHabitId(habitId, size, offset);
    }

    private Habit getHabitByIdOrThrow(UUID habitId) {
        List<Habit> habits = habitRepository.getHabitById(habitId);
        if (habits == null || habits.isEmpty()) {
            throw new NotFoundException("Habit not found");
        }
        return habits.get(0);
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
