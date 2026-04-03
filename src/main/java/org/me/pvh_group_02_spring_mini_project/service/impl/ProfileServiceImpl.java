package org.me.pvh_group_02_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.me.pvh_group_02_spring_mini_project.exception.NotFoundException;
import org.me.pvh_group_02_spring_mini_project.model.entity.AppUser;
import org.me.pvh_group_02_spring_mini_project.model.request.EditUserProfileRequest;
import org.me.pvh_group_02_spring_mini_project.model.response.AppUserResponse;
import org.me.pvh_group_02_spring_mini_project.repository.AppUserRepository;
import org.me.pvh_group_02_spring_mini_project.repository.ProfileRepository;
import org.me.pvh_group_02_spring_mini_project.service.ProfileService;
import org.me.pvh_group_02_spring_mini_project.utils.HandleCurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;
    private final AppUserRepository appUserRepository;
    private final HandleCurrentUser handleCurrentUser;

    @Override
    public AppUserResponse getUserProfile() {
        UUID currentUser = handleCurrentUser.getUserIdOfCurrentUser();
        AppUser user = appUserRepository.getUserById(currentUser);
        return modelMapper.map(user, AppUserResponse.class);
    }

    @Override
    public AppUserResponse updateUserProfile(EditUserProfileRequest editRequest) {
        UUID currentUserId = handleCurrentUser.getUserIdOfCurrentUser();
        AppUser user = appUserRepository.getUserById(currentUserId);

        if (user == null) {
            throw new NotFoundException("User not found with id: " + currentUserId);
        }

        return profileRepository.updateUserProfile(user.getEmail(), editRequest);
    }

    @Override
    public void deleteUserProfile() {
        UUID currentUserId = handleCurrentUser.getUserIdOfCurrentUser();
        AppUser user = appUserRepository.getUserById(currentUserId);

        if (user == null) {
            throw new NotFoundException("User not found with id: " + currentUserId);
        }

        profileRepository.deleteUserProfile(user.getEmail());
    }
}
