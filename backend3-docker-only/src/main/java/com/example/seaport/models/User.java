package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

     private String firstname;
     private String lastname;
     private String address;
     private String department;
     @Column(name = "password", length = 200,nullable = false)
     private String password;
     @Column(name = "is_active")
     private int isActive;
     @Column(name = "date_of_birth")
     private Date dateOfBirth;
    @Column(name = "phone", length = 10, nullable = false)
    private String phone;
    @Column(name = "facebook_account_id")
    private int facebookAccountId;
    @Column(name = "google_account_id")
    private int googleAccountId;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}

