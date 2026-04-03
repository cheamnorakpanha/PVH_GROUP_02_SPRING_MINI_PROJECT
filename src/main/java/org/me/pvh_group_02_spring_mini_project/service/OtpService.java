package org.me.pvh_group_02_spring_mini_project.service;

public interface OtpService {
    String generateOtp();

    void sendOtp(String email, String OTP, long ttl);

    boolean verifyOtp(String email, String OTP);

    boolean isOtpPresent(String email);
}