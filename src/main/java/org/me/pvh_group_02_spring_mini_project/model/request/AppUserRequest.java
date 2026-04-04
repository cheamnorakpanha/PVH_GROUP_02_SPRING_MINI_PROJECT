package org.me.pvh_group_02_spring_mini_project.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequest {
    @NotBlank(message = "must not be blank")
    @NotNull
    @Schema(example = "Endy")
    @Size(min = 1, max = 50, message = "Username must contain 1 - 50")
    private String userName;

    @NotBlank(message = "must be a well-formed email address")
    @NotNull
    @Email(message = "Invalid email format")
    @Schema(example = "diixnew@gmail.com")
    private String email;


    @NotNull
    @NotBlank(message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    @Schema(example = "Qwer123@")

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    private String profileImageUrl;
}
