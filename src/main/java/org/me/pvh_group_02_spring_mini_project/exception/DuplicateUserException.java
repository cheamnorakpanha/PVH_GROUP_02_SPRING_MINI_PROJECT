package org.me.pvh_group_02_spring_mini_project.exception;

public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException() {
    }

    public DuplicateUserException(String message) {
        super(message);
    }
}
