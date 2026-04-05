package org.me.pvh_group_02_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get all achievements")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Achievement>>> getAllAchievement(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        List<Achievement> achievements = achievementService.getAllAchievement(page, size);
        ApiResponse<List<Achievement>> response = ApiResponse.<List<Achievement>>builder()
                .success(true)
                .timestamp(Instant.now())
                .status(HttpStatus.OK.name())
                .message("Achievement fetched successfully")
                .payload(achievements )
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @Operation(summary = "Get achievements By App User ID")
    @GetMapping("/app-users")
    public ResponseEntity<ApiResponse<List<Achievement>>> getAchievementByAppUserId(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            Authentication authentication) {
        AppUser appUser = (AppUser)  authentication.getPrincipal();
        UUID appUserId = appUser.getAppUserId();
        List<Achievement> achievements = achievementService.getAchievementByAppUserId( appUserId, page, size);
        ApiResponse<List<Achievement>> response = ApiResponse.<List<Achievement>>builder()
                .success(true)
                .timestamp(Instant.now())
                .status(HttpStatus.OK.name())
                .message("Achievements for the specified App User retrieved successfully!")
                .payload(achievements )
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}

