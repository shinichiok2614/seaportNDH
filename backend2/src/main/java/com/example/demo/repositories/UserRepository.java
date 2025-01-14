package com.example.demo.repositories;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByPhone(String phone);

    Optional<User> findByPhone(String phone);
    //Optional: isEmpty: null, isPresent: khac null
}
