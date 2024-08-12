package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostImage {
    public static final int MAX_IMAGE = 5;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "post_id")               //k can kiem soat vi du lieu da co truoc do->co the dung doi tuong
    private Post post;

    @Column(name = "image_url", length = 300)   //can kiem soat den tung ki tu vi du lieu tho
    private String imageUrl;

}
