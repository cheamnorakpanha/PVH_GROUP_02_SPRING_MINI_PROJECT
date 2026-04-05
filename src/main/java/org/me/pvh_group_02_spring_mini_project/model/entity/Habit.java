package org.me.pvh_group_02_spring_mini_project.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.me.pvh_group_02_spring_mini_project.model.response.AppUserResponse;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Habit {
    private UUID habitId;
    private String title;
    private String description;
    private String frequency;
    private Boolean isActive;
//    private UUID appUserId;
    private AppUserResponse appUserResponse;
    private Instant createdAt;
}
