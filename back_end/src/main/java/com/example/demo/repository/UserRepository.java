package com.example.demo.repository;

import com.example.demo.model.UserModel;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndPassword(String name, String password);
}
