package org.me.pvh_group_02_spring_mini_project.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.me.pvh_group_02_spring_mini_project.service.OtpService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final Map<String, String> otpStorage = new HashMap<>();
    private final Map<String, LocalDateTime> otpExpiry = new HashMap<>();

    @Override
    public String generateOtp() {
        double randomOtp = Math.random() * 1000000;
        log.info("Generated OTP {}", randomOtp);
        return String.valueOf(Math.round(randomOtp));
    }

    @Override
    public void sendOtp(String email, String otp, long ttl) {
        otpStorage.put(email, otp);
        otpExpiry.put(email, LocalDateTime.now().plusSeconds(ttl));

        // Thymeleaf context — maps to ${otp} and ${expiryMinutes} in your template
        Context context = new Context();
        context.setVariable("otp", otp);
        context.setVariable("expiryMinutes", ttl / 60); // convert seconds → minutes

        // Render otp-email.html
        String htmlContent = templateEngine.process("otp-email", context);

        // Send as HTML email
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("Verify your email with OTP");
            helper.setText(htmlContent, true); // true = send as HTML
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Failed to send OTP email to {}", email, e);
            throw new RuntimeException("Failed to send OTP email", e);
        }

        log.info("Sent OTP {} to email {}", otp, email);
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        String storedOtp = otpStorage.get(email);
        LocalDateTime expiry = otpExpiry.get(email);
        log.info("Received OTP {} from {}", otp, email);

        if (storedOtp != null && expiry != null
                && LocalDateTime.now().isBefore(expiry)
                && storedOtp.equals(otp)) {
            otpStorage.remove(email);
            otpExpiry.remove(email);
            return true;
        }
        return false;
    }

    public boolean isOtpPresent(String email) {
        LocalDateTime expiry = otpExpiry.get(email);
        return otpStorage.containsKey(email)
                && expiry != null
                && LocalDateTime.now().isBefore(expiry);
    }
}