package org.me.pvh_group_02_spring_mini_project.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class CreateHabitException extends RuntimeException {

    private final Map<String, String> errors;

    public CreateHabitException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

}
