package com.businesscenterservices.businesscenterservices.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider tokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        logger.debug("Traitement de la requête: {} {}", request.getMethod(), request.getRequestURI());
        logger.debug("Headers de la requête: {}", Collections.list(request.getHeaderNames())
                .stream()
                .map(header -> header + ": " + request.getHeader(header))
                .collect(Collectors.joining(", ")));

        // Gestion explicite des requêtes OPTIONS
        if (request.getMethod().equals("OPTIONS")) {
            logger.debug("Traitement de la requête OPTIONS");
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
            response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Pour les URLs publiques comme /api/login, on laisse passer sans vérifier le token
        if (isPublicUrl(request)) {
            logger.debug("URL publique détectée, pas de vérification du token: {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = getJwtFromRequest(request);
            logger.debug("JWT trouvé dans la requête: {}", jwt != null ? "oui" : "non");

            if (jwt != null && tokenProvider.validateToken(jwt)) {
                String username = tokenProvider.getUsernameFromJWT(jwt);
                logger.debug("JWT valide trouvé pour l'utilisateur: {}", username);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, tokenProvider.getAuthorities(jwt));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Impossible de définir l'authentification de l'utilisateur", ex);
            SecurityContextHolder.clearContext();
        }

        // Ajout des headers CORS pour toutes les réponses
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
        filterChain.doFilter(request, response);
    }

    private boolean isPublicUrl(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean isPublic = path.equals("/api/login") ||
                          path.startsWith("/api/auth/") ||
                          path.equals("/error");
        logger.debug("Vérification URL publique: {} -> {}", path, isPublic);
        return isPublic;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
} 