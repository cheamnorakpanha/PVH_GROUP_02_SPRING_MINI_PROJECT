package org.me.pvh_group_02_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.me.pvh_group_02_spring_mini_project.model.request.EditUserProfileRequest;
import org.me.pvh_group_02_spring_mini_project.model.response.ApiResponse;
import org.me.pvh_group_02_spring_mini_project.model.response.AppUserResponse;
import org.me.pvh_group_02_spring_mini_project.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping()
    public ResponseEntity<ApiResponse<AppUserResponse>> getUserProfile(){

        ApiResponse<AppUserResponse> response = ApiResponse.<AppUserResponse>builder()
                .success(true)
                .message("User profile fetched successfully!")
                .status(HttpStatus.OK.name())
                .payload(profileService.getUserProfile())
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping()
    public ResponseEntity<ApiResponse<AppUserResponse>> updateUserProfile(@RequestBody EditUserProfileRequest editRequest){
        AppUserResponse user = profileService.updateUserProfile(editRequest);
        ApiResponse<AppUserResponse> response = ApiResponse.<AppUserResponse>builder()
                .success(true)
                .message("User profile updated successfully!")
                .status(HttpStatus.OK.name())
                .payload(user)
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping()
    public ResponseEntity<ApiResponse<Void>> deleteUserProfile(){
        profileService.deleteUserProfile();
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("User profile deleted successfully!")
                .status(HttpStatus.OK.name())
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}




