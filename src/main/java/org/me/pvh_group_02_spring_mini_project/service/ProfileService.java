package org.me.pvh_group_02_spring_mini_project.service;

import org.me.pvh_group_02_spring_mini_project.model.entity.AppUser;
import org.me.pvh_group_02_spring_mini_project.model.response.UserProfileResponse;

public interface ProfileService {
    UserProfileResponse getUserProfile();
}
