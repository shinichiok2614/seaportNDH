package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "social_accounts")
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String provider;
    @Column(name = "provider_id", length = 50, nullable = false)
    private String providerId;
    @Column(name = "email", length = 150, nullable = false)
    private String email;
    @Column(name = "name", length = 100,nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
