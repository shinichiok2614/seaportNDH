package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @Column(name = "name", length = 20)
    private String name;

    public static String ADMIN = "ADMIN";
    public static String USER = "USER";
}
