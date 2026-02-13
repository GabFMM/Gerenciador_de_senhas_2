package com.gabfmm.gerenciador_de_senhas.repository;

import com.gabfmm.gerenciador_de_senhas.model.AccountModel;
import com.gabfmm.gerenciador_de_senhas.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountModel, Long> {

    // -- EXISTS

    boolean existsByTitleAndUser_Id(String title, Long id);

    // -- FIND --

    Optional<AccountModel> findByTitleAndUser_Id(String title, Long id);

    List<AccountModel> findByUser_IdOrderByTitleAsc(Long id);

    List<AccountModel> findByUserIdAndTitleContainingOrderByTitleAsc(Long userId, String title);

    // -- DELETE --

    @Transactional
    @Modifying
    @Query("delete from AccountModel a where a.title = ?1 and a.user.id = ?2")
    void deleteByTitleAndUser_id(String title, Long user_id);
}
