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

    boolean existsByNameHash(String name);

    // -- FIND --

    @Query("select id from UserModel where nameHash = :nameHash")
    Optional<Long> findIdByNameHash(@Param("nameHash") String name);

    @Query("select nameEncrypted from UserModel where id = :id")
    Optional<String> findNameEncryptedById(@Param("id") Long id);

    @Query("select passwordHash from UserModel where id = :id")
    Optional<String> findPasswordHashById(@Param("id") Long id);

    @Query("select passwordHash from UserModel where nameHash = :nameHash")
    Optional<String> findPasswordHashByNameHash(@Param("nameHash") String name);

    // -- UPDATE --

    @Transactional
    @Modifying
    @Query("update UserModel u set u.nameEncrypted = ?1 where u.id = ?2")
    void updateNameEncryptedById(String name, Long id);

    @Transactional
    @Modifying
    @Query("update UserModel u set u.nameHash = ?1 where u.id = ?2")
    void updateNameHashById(String name, Long id);

    @Transactional
    @Modifying
    @Query("update UserModel u set u.passwordHash = ?1 where u.id = ?2")
    void updatePasswordHashById(String passwordHash, Long id);

    // -- DELETE --

    @Transactional
    @Modifying
    void deleteById(Long id);
}
