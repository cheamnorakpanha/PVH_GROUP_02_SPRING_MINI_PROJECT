package org.me.pvh_group_02_spring_mini_project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitLog {
    private UUID habitLogId;
    private LocalDate logDate;
    private String status;
    private Integer xpEarned;
    private UUID habitId;
}