package com.gabfmm.gerenciador_de_senhas.repository;

import com.gabfmm.gerenciador_de_senhas.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountModel, Long> {

    // -- EXISTS

    boolean existsByTitleHashAndUser_Id(String title, Long id);

    // -- FIND --

    List<AccountModel> findByUser_Id(Long userId);

    Optional<AccountModel> findByTitleHashAndUser_Id(String title, Long id);

    // -- DELETE --

    @Transactional
    @Modifying
    @Query("delete from AccountModel a where a.titleHash = ?1 and a.user.id = ?2")
    void deleteByTitleHashAndUser_id(String title, Long user_id);
}
