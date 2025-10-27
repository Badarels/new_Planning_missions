package com.businesscenterservices.businesscenterservices.security;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class PasswordValidationService {
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 50;
    private static final Pattern SPECIAL_CHARS = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");
    private static final Pattern UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern NUMBERS = Pattern.compile("[0-9]");

    public boolean isPasswordValid(String password) {
        if (password == null || password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            return false;
        }

        return UPPERCASE.matcher(password).find() &&
               LOWERCASE.matcher(password).find() &&
               NUMBERS.matcher(password).find() &&
               SPECIAL_CHARS.matcher(password).find();
    }

    public String getPasswordRequirements() {
        return String.format(
            "Le mot de passe doit contenir :\n" +
            "- Entre %d et %d caractères\n" +
            "- Au moins une lettre majuscule\n" +
            "- Au moins une lettre minuscule\n" +
            "- Au moins un chiffre\n" +
            "- Au moins un caractère spécial parmi !@#$%%^&*(),.?\":{}|<>",
            MIN_LENGTH, MAX_LENGTH
        );
    }
} 