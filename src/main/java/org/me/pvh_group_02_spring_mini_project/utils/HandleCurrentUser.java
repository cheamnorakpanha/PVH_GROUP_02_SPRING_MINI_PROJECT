package org.me.pvh_group_02_spring_mini_project.utils;

import org.me.pvh_group_02_spring_mini_project.model.entity.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class HandleCurrentUser {
    public UUID getUserIdOfCurrentUser() {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UUID userId = appUser.getAppUserId();
        System.out.println(userId);
        return userId;
    }

    public String getUserByEmail() {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String email = appUser.getEmail();
        System.out.println(email);
        return email;
    }
}
