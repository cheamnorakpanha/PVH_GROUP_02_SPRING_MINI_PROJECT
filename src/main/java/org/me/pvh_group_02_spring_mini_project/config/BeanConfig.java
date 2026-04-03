package org.me.pvh_group_02_spring_mini_project.config;

import org.me.pvh_group_02_spring_mini_project.model.entity.AppUser;
import org.me.pvh_group_02_spring_mini_project.model.response.UserProfileResponse;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(AppUser.class, UserProfileResponse.class).addMappings(mapper -> {
            mapper.map(AppUser::getUserId,   UserProfileResponse::setUserId);
            mapper.map(AppUser::getActualUsername, UserProfileResponse::setUsername);
        });

        return modelMapper;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}