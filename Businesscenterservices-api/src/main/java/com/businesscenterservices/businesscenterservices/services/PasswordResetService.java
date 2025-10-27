package com.businesscenterservices.businesscenterservices.services;

import com.businesscenterservices.businesscenterservices.entities.PasswordResetToken;
import com.businesscenterservices.businesscenterservices.entities.Users;
import com.businesscenterservices.businesscenterservices.exceptions.InvalidTokenException;
import com.businesscenterservices.businesscenterservices.repositories.PasswordResetTokenRepository;
import com.businesscenterservices.businesscenterservices.repositories.UsersRepository;
import com.businesscenterservices.businesscenterservices.security.PasswordValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UsersRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidationService passwordValidationService;

    @Transactional
    public void createPasswordResetTokenForUser(String email) {
        Users user = userRepository.findByEmailUser(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            PasswordResetToken myToken = new PasswordResetToken();
            myToken.setToken(token);
            myToken.setUser(user);
            myToken.setExpiryDate(LocalDateTime.now().plusHours(24));
            
            tokenRepository.save(myToken);
            
            try {
                emailService.sendPasswordResetEmail(user.getEmailUser(), token);
            } catch (Exception e) {
                throw new RuntimeException("Erreur lors de l'envoi de l'email de réinitialisation");
            }
        }
    }

    @Transactional
    public void validatePasswordResetToken(String token) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new InvalidTokenException("Token invalide"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(resetToken);
            throw new InvalidTokenException("Token expiré");
        }
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        if (!passwordValidationService.isPasswordValid(newPassword)) {
            throw new InvalidTokenException(passwordValidationService.getPasswordRequirements());
        }

        PasswordResetToken resetToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new InvalidTokenException("Token invalide"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(resetToken);
            throw new InvalidTokenException("Token expiré");
        }

        Users user = resetToken.getUser();
        user.setPasswordUser(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(resetToken);
    }
} 