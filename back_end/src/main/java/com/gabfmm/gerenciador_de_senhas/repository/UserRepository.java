package com.gabfmm.gerenciador_de_senhas.repository;

import com.gabfmm.gerenciador_de_senhas.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndPassword(String name, String password);
}
