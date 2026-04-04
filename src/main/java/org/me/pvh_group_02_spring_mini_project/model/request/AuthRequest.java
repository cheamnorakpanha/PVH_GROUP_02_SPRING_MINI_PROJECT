package org.me.pvh_group_02_spring_mini_project.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotBlank
    @NotNull
    @Schema(example = "username or email")
    private String identifier;
    @NotNull
    @NotBlank
    @Schema(example = "Qwer123@")
    private String password;
}
