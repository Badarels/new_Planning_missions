package com.businesscenterservices.businesscenterservices.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SecurityAuditService {

    public void auditLoginAttempt(String username, boolean success, HttpServletRequest request) {
        Map<String, Object> auditData = new HashMap<>();
        auditData.put("timestamp", LocalDateTime.now());
        auditData.put("action", "LOGIN_ATTEMPT");
        auditData.put("username", username);
        auditData.put("success", success);
        auditData.put("ipAddress", getClientIP(request));
        auditData.put("userAgent", request.getHeader("User-Agent"));

        log.info("Audit de sécurité - Tentative de connexion: {}", auditData);
    }

    public void auditPasswordChange(String username, boolean success, HttpServletRequest request) {
        Map<String, Object> auditData = new HashMap<>();
        auditData.put("timestamp", LocalDateTime.now());
        auditData.put("action", "PASSWORD_CHANGE");
        auditData.put("username", username);
        auditData.put("success", success);
        auditData.put("ipAddress", getClientIP(request));
        auditData.put("userAgent", request.getHeader("User-Agent"));

        log.info("Audit de sécurité - Changement de mot de passe: {}", auditData);
    }

    public void auditAccountLocked(String username, String reason, HttpServletRequest request) {
        Map<String, Object> auditData = new HashMap<>();
        auditData.put("timestamp", LocalDateTime.now());
        auditData.put("action", "ACCOUNT_LOCKED");
        auditData.put("username", username);
        auditData.put("reason", reason);
        auditData.put("ipAddress", getClientIP(request));
        auditData.put("userAgent", request.getHeader("User-Agent"));

        log.warn("Audit de sécurité - Compte verrouillé: {}", auditData);
    }

    public void auditUnauthorizedAccess(String username, String resource, HttpServletRequest request) {
        Map<String, Object> auditData = new HashMap<>();
        auditData.put("timestamp", LocalDateTime.now());
        auditData.put("action", "UNAUTHORIZED_ACCESS");
        auditData.put("username", username);
        auditData.put("resource", resource);
        auditData.put("ipAddress", getClientIP(request));
        auditData.put("userAgent", request.getHeader("User-Agent"));
        auditData.put("requestMethod", request.getMethod());
        auditData.put("requestURI", request.getRequestURI());

        log.warn("Audit de sécurité - Accès non autorisé: {}", auditData);
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
} 