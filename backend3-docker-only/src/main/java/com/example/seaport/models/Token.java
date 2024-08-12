package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "token", length = 255, nullable = false)
    private String token;
    @Column(name = "token_type", length = 50, nullable = false)
    private String tokenType;
    @Column(name = "expiration_date")
    private Date expirationDate;
    @Column(name = "revoked", length = 1, nullable = false)
    private int revoked;
    @Column(name = "expired", length = 1, nullable = false)
    private int expired;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
