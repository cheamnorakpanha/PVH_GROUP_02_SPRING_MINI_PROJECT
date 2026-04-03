package org.me.pvh_group_02_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.me.pvh_group_02_spring_mini_project.model.response.ApiResponse;
import org.me.pvh_group_02_spring_mini_project.model.response.AppUserResponse;
import org.me.pvh_group_02_spring_mini_project.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {
    private final ProfileService profileService;
    @GetMapping()
    public ResponseEntity<ApiResponse<AppUserResponse>> getUserProfile(){
        AppUserResponse user = profileService.getUserProfile();
        ApiResponse<AppUserResponse> response = ApiResponse.<AppUserResponse>builder()
                .success(true)
                .message("User profile fetched successfully!")
                .status(HttpStatus.OK)
                .payload(user)
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
