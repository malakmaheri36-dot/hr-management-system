package com.solutions.hrsystem.services;

import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    // Simple encoding (for demo - not real encryption)
    public String encodePassword(String rawPassword) {
        // In production, use BCryptPasswordEncoder
        return "encoded_" + rawPassword;
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return encodedPassword.equals("encoded_" + rawPassword);
    }
}
