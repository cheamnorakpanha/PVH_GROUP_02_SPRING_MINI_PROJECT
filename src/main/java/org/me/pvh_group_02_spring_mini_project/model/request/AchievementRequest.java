package org.me.pvh_group_02_spring_mini_project.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AchievementRequest {
    private String title;
    private String description;
    private String badge;
    private Integer xpRequired;
}
