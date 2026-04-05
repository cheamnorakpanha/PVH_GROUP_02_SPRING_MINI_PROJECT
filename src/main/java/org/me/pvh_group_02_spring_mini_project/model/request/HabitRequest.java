package org.me.pvh_group_02_spring_mini_project.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties("active")
public class HabitRequest {
    @NotBlank(message = "must not be blank")
    private String title;
    @NotBlank(message = "must not be blank")
    private String description;

    @Schema(example = "DAILY")
    private String frequency;
}
