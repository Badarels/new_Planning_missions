package com.businesscenterservices.businesscenterservices.security;

import com.businesscenterservices.businesscenterservices.services.EmailService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TwoFactorAuthService {

    private final EmailService emailService;
    private static final int CODE_LENGTH = 6;
    private static final int CODE_EXPIRATION_MINUTES = 5;

    private LoadingCache<String, String> otpCache = CacheBuilder.newBuilder()
            .expireAfterWrite(CODE_EXPIRATION_MINUTES, TimeUnit.MINUTES)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) {
                    return "";
                }
            });

    public void generateAndSendCode(String username) {
        String code = generateCode();
        otpCache.put(username, code);
        
        try {
            emailService.sendTwoFactorCode(username, code);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'envoi du code 2FA");
        }
    }

    public boolean verifyCode(String username, String code) {
        String storedCode = otpCache.getIfPresent(username);
        if (storedCode != null && storedCode.equals(code)) {
            otpCache.invalidate(username);
            return true;
        }
        return false;
    }

    private String generateCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();
        
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        
        return code.toString();
    }

    public int getCodeExpirationMinutes() {
        return CODE_EXPIRATION_MINUTES;
    }

    public boolean isTwoFactorEnabled(String username) {
        // TODO: Implémenter la vérification de l'état 2FA depuis la base de données
        return false; // Par défaut, 2FA est désactivé
    }
} 