package org.me.pvh_group_02_spring_mini_project.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequest {
    private String userName;
    private String email;
    private String password;
    private String profileImageUrl;
}
