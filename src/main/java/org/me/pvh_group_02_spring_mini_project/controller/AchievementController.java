package org.me.pvh_group_02_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.me.pvh_group_02_spring_mini_project.model.entity.Achievement;
import org.me.pvh_group_02_spring_mini_project.model.entity.AppUser;
import org.me.pvh_group_02_spring_mini_project.model.response.ApiResponse;
import org.me.pvh_group_02_spring_mini_project.service.AchievementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("api/v1/achievements")
public class AchievementController {
    private final AchievementService achievementService;

    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<Achievement>>> getAllAchievement(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        List<Achievement> achievements = achievementService.getAllAchievement(page, size);
        ApiResponse<List<Achievement>> response = ApiResponse.<List<Achievement>>builder()
                .success(true)
                .timestamp(Instant.now())
                .status(HttpStatus.OK)
                .message("Achievement fetched successfully")
                .payload(achievements )
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/app-users/{appUserId}")
    public ResponseEntity<ApiResponse<List<Achievement>>> getAchievementByAppUserId(

            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        List<Achievement> achievements = achievementService.getAchievementByAppUserId( page, size);
        ApiResponse<List<Achievement>> response = ApiResponse.<List<Achievement>>builder()
                .success(true)
                .timestamp(Instant.now())
                .status(HttpStatus.OK)
                .message("Achievements for the specified App User retrieved successfully!")
                .payload(achievements )
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}

