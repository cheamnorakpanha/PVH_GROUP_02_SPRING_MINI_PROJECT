package org.me.pvh_group_02_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.me.pvh_group_02_spring_mini_project.exception.BadRequestException;
import org.me.pvh_group_02_spring_mini_project.exception.DuplicateUserException;
import org.me.pvh_group_02_spring_mini_project.exception.NotFoundException;
import org.me.pvh_group_02_spring_mini_project.model.entity.AppUser;
import org.me.pvh_group_02_spring_mini_project.model.request.AppUserRequest;
import org.me.pvh_group_02_spring_mini_project.model.response.AppUserResponse;
import org.me.pvh_group_02_spring_mini_project.repository.AppUserRepository;
import org.me.pvh_group_02_spring_mini_project.service.AppUserService;
import org.me.pvh_group_02_spring_mini_project.service.OtpService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserServiceImpl implements AppUserService {
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;
    private final OtpService otpService;
    private final AppUser appUser;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        AppUser user = appUserRepository.getUserByEmailOrUsername(identifier);
        if (user == null) throw new UsernameNotFoundException("User not found");
        System.out.println("User logging in: " + identifier);
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

    @Override
    public boolean getOtp(String email) {
        try {
            var generatedOtp = otpService.generateOtp();
            log.info("Generated OTP: {}", generatedOtp);
            otpService.sendOtp(email, generatedOtp, 120);
            return true;
        }catch (Exception e) {
            log.error("Error while generating OTP: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public void authenticate(String email, String otp) {
        if (email == null || otp == null) {
            throw new BadRequestException("Email or OTP cannot be null");
        }

        AppUser user = appUserRepository.getUserByEmail(email);
        if (user == null) {
            throw new BadRequestException("The email address provided is not registered. Please check and try again.");
        }

//        if (user.isVerified()) {
//            throw new BadRequestException("Account is already verified.");
//        }

        boolean verified = otpService.verifyOtp(email, otp);
        if (!verified) {
            throw new BadRequestException("The OTP entered is invalid or has expired. Please request a new OTP and try again");
        }

        appUserRepository.verifyUser(email);
    }

    @Override
    public void  resendOtp(String email) {
        AppUser existingUser = appUserRepository.getUserByEmail(email);
        if (existingUser == null) {
            throw new BadRequestException("This email is not registered. Please register first.");
        }
        if (existingUser.isVerified()) {
            throw new BadRequestException("Account is already verified.");
        }
        getOtp(email);
    }
}
