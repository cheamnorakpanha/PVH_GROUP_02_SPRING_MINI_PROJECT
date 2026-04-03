package org.me.pvh_group_02_spring_mini_project.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    @JsonProperty("appUserId")
    private UUID userId;

    private String username;
    private String email;
    private Integer level;
    private Integer xp;
    private String profileImageUrl;
    private boolean isVerified;
    private Instant createdAt;

}
