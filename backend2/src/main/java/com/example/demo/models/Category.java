package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //tu sinh, k giong nhau
//    @Column(name = "id")  //anh xa neu khac nhau
    private Long id;
//    @Column(name = "name", nullable = false, length = 350)
    @Column(name = "name", nullable = false)
    private String name;


}
