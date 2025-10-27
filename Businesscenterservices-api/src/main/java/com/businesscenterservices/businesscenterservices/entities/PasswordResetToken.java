package com.businesscenterservices.businesscenterservices.entities;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class PasswordResetToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private Users user;

    private LocalDateTime expiryDate;
} 