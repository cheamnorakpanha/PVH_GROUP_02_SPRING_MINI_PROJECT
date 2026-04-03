package org.me.pvh_group_02_spring_mini_project.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserResponse {
    private UUID appUserId;
    private String userName;
    private String email;
    private String password;
    private Integer level;
    private Integer xp;
    private String profileImageUrl;
    private boolean isVerified;
    private Instant createdAt;
}
