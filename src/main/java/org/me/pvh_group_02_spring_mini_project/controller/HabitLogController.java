package org.me.pvh_group_02_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.me.pvh_group_02_spring_mini_project.model.entity.HabitLog;
import org.me.pvh_group_02_spring_mini_project.model.request.HabitLogRequest;
import org.me.pvh_group_02_spring_mini_project.model.response.ApiResponse;
import org.me.pvh_group_02_spring_mini_project.service.HabitLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/habit-logs")
@Validated
@SecurityRequirement(name = "bearerAuth")
public class HabitLogController {

    private final HabitLogService habitLogService;

    @PostMapping
    public ResponseEntity<ApiResponse<HabitLog>> createHabitLog(@Valid @RequestBody HabitLogRequest request) {
        HabitLog log = habitLogService.createHabitLog(request.getHabitId(), request.getStatus());

        ApiResponse<HabitLog> response = ApiResponse.<HabitLog>builder()
                .timestamp(Instant.now())
                .message("Habit log created successfully!")
                .success(true)
                .status(HttpStatus.CREATED.name())
                .payload(log)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/habit/{habitId}")
    public ResponseEntity<ApiResponse<List<HabitLog>>> getLogsByHabitId(
            @PathVariable UUID habitId,
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) Integer size) {

        List<HabitLog> habitLogs = habitLogService.getAllHabitLogByHabitId(habitId, page, size);

        ApiResponse<List<HabitLog>> response = ApiResponse.<List<HabitLog>>builder()
                .timestamp(Instant.now())
                .message("Fetched all habit logs for the specified habit ID successfully!")
                .success(true)
                .status(HttpStatus.OK.name())
                .payload(habitLogs)
                .build();

        return ResponseEntity.ok(response);
    }
}
