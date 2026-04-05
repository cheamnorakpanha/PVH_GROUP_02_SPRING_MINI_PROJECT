package org.me.pvh_group_02_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.me.pvh_group_02_spring_mini_project.model.entity.Habit;
import org.me.pvh_group_02_spring_mini_project.model.request.HabitRequest;
import org.me.pvh_group_02_spring_mini_project.model.response.ApiResponse;
import org.me.pvh_group_02_spring_mini_project.service.HabitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/habits")
@SecurityRequirement(name = "bearerAuth")
public class HabitController {
    private final HabitService habitService;

    @Operation(summary = "Get all habits")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Habit>>> getAllHabits(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        ApiResponse<List<Habit>> response = ApiResponse.<List<Habit>>builder()
                .success(true)
                .message("Fetched all habits successfully!")
                .status(HttpStatus.OK.name())
                .payload(habitService.getAllHabits(page, size))
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Get habit by Id")
    @GetMapping("/{habit-id}")
    public ResponseEntity<ApiResponse<List<Habit>>> getHabitById(@PathVariable("habit-id") UUID habitId) {
        ApiResponse<List<Habit>> response = ApiResponse.<List<Habit>>builder()
                .success(true)
                .message("Habit fetched successfully!")
                .status(HttpStatus.OK.name())
                .payload(habitService.getHabitById(habitId))
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Create a new habit")
    @PostMapping
    public ResponseEntity<ApiResponse<Habit>> createNewHabit(@RequestBody HabitRequest request) {
        ApiResponse<Habit> response = ApiResponse.<Habit>builder()
                .success(true)
                .message("Habit created successfully!")
                .status(HttpStatus.CREATED.name())
                .payload(habitService.createNewHabit(request))
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update habit by Id")
    @PutMapping("/{habit-id}")
    public ResponseEntity<ApiResponse<Habit>> updateHabitById(@PathVariable("habit-id") UUID habitId, @RequestBody HabitRequest request) {
        ApiResponse<Habit> response = ApiResponse.<Habit>builder()
                .success(true)
                .message("Habit updated successfully!")
                .status(HttpStatus.OK.name())
                .payload(habitService.updateHabitById(habitId, request))
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Delete habit by Id")
    @DeleteMapping("/{habit-id}")
    public ResponseEntity<ApiResponse<String>> deleteHabitById(@PathVariable("habit-id") UUID habitId) {
        habitService.deleteHabitById(habitId);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .message("Habit deleted successfully!")
                .status(HttpStatus.OK.name())
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}









