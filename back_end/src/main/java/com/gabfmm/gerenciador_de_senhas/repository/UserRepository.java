package com.gabfmm.gerenciador_de_senhas.repository;

import com.gabfmm.gerenciador_de_senhas.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    // -- EXISTS --

    boolean existsByName(String name);

    boolean existsByNameAndPassword(String name, String password);

    boolean existsByIdAndPassword(Long id, String password);

    // -- FIND --

    @Query("select id from UserModel where name = :name")
    Optional<Long> findIdByName(@Param("name") String name);

    @Query("select name from UserModel where id = :id")
    Optional<String> findNameById(@Param("id") Long id);

    @Query("select password from UserModel where id = :id")
    Optional<String> findPasswordById(@Param("id") Long id);

    // -- UPDATE --

    @Transactional
    @Modifying
    @Query("update UserModel u set u.name = ?1 where u.id = ?2")
    void updateNameById(String name, Long id);

    @Transactional
    @Modifying
    @Query("update UserModel u set u.password = ?1 where u.id = ?2")
    void updatePasswordById(String password, Long id);

    // -- DELETE --

    @Transactional
    @Modifying
    void deleteById(Long id);
}
