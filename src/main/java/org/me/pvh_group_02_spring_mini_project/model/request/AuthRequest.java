package org.me.pvh_group_02_spring_mini_project.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @Schema(example = "diixnew@gmail.com")
    private String email;
    @Schema(example = "qwer1234")
    private String password;
}
