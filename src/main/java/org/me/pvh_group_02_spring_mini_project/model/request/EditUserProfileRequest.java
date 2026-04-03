package org.me.pvh_group_02_spring_mini_project.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditUserProfileRequest {
    @NotBlank(message = "User name must not be blank")
    @NotNull
    private String userName;
    @NotBlank(message = "Image url must not be blank")
    private String profileImageUrl;
}
