package org.me.pvh_group_02_spring_mini_project.service;

import org.me.pvh_group_02_spring_mini_project.model.entity.AppUser;
import org.me.pvh_group_02_spring_mini_project.model.request.AppUserRequest;
import org.me.pvh_group_02_spring_mini_project.model.response.AppUserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    AppUserResponse register(AppUserRequest request);
}
