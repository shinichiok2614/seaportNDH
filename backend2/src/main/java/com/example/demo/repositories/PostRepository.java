package com.example.demo.repositories;

import com.example.demo.models.Category;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByName(String name);

    Page<Post> findAll(Pageable pageable);

    List<User> findByUserId(Long userId); //Tim tat ca bai cua 1 user nao do

    List<Category> findByCategoryId(Long categoryId);
}
