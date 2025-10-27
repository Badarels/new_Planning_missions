package com.businesscenterservices.businesscenterservices.services;

import com.businesscenterservices.businesscenterservices.dto.PasswordChangeRequest;
import com.businesscenterservices.businesscenterservices.exceptions.InvalidPasswordException;
import com.businesscenterservices.businesscenterservices.security.PasswordValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final UserServicesImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidationService passwordValidationService;

    @Transactional
    public void changePassword(String username, PasswordChangeRequest request) {
        // Vérifier que le nouveau mot de passe et la confirmation correspondent
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InvalidPasswordException("Le nouveau mot de passe et sa confirmation ne correspondent pas");
        }

        // Vérifier que le nouveau mot de passe est valide
        if (!passwordValidationService.isPasswordValid(request.getNewPassword())) {
            throw new InvalidPasswordException(passwordValidationService.getPasswordRequirements());
        }

        // Vérifier que l'ancien mot de passe est correct
        var user = userService.loadUserByNomUser(username);
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPasswordUser())) {
            throw new InvalidPasswordException("L'ancien mot de passe est incorrect");
        }

        // Encoder et sauvegarder le nouveau mot de passe
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        userService.updatePassword(username, encodedPassword);
    }
} 