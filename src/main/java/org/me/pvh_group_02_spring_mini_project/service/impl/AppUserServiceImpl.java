package org.me.pvh_group_02_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.me.pvh_group_02_spring_mini_project.exception.DuplicateUserException;
import org.me.pvh_group_02_spring_mini_project.model.entity.AppUser;
import org.me.pvh_group_02_spring_mini_project.model.request.AppUserRequest;
import org.me.pvh_group_02_spring_mini_project.model.response.AppUserResponse;
import org.me.pvh_group_02_spring_mini_project.repository.AppUserRepository;
import org.me.pvh_group_02_spring_mini_project.service.AppUserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = appUserRepository.getUserByEmail(email);
        if (user == null) throw new UsernameNotFoundException("User not found");
        System.out.println("User logging in: " + email);
        System.out.println("Authorities found: " + user.getAuthorities());
        return user;
    }

    @Override
    public AppUserResponse register(AppUserRequest request) {

        if (appUserRepository.existsUserName(request.getUserName())) {
            throw new DuplicateUserException("The username is already associated with an existing account. Please try with a different one.");
        }

        if (appUserRepository.existsUserEmail(request.getEmail())){
            throw new DuplicateUserException("The email is already associated with an existing account. Please try with a different one.");
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        AppUser appUser = appUserRepository.register(request);
        return modelMapper.map(appUserRepository.getUserById(appUser.getAppUserId()), AppUserResponse.class);
    }
}
