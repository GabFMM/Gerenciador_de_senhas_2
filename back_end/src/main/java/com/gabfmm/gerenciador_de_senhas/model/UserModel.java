package com.gabfmm.gerenciador_de_senhas.model;

import com.gabfmm.gerenciador_de_senhas.converter.CryptographyConverter;
import com.gabfmm.gerenciador_de_senhas.converter.HashConverter;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
public class UserModel {

    // -- Fields --

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 512)
    @Convert(converter = CryptographyConverter.class)
    private String nameEncrypted;

    @Column(nullable = false, unique = true, length = 100)
    @Convert(converter = HashConverter.class)
    private String nameHash;

    @Column(nullable = false, length = 100)
    private String passwordHash;

    // -- Attributes --

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<AccountModel> accounts;

    // -- Methods --

    public UserModel() {
        accounts = new HashSet<>();
    }

    public void setNameEncrypted(String nameEncrypted) {
        this.nameEncrypted = nameEncrypted;
    }

    public void setNameHash(final String nameHash){
        this.nameHash = nameHash;
    }

    public void setPasswordHash(final String passwordHash){
        this.passwordHash = passwordHash;
    }

    public void setAccounts(final Set<AccountModel> accounts) {
        this.accounts = accounts;
    }

    public Long getId(){
        return id;
    }

    public String getNameEncrypted() {
        return nameEncrypted;
    }

    public String getNameHash(){
        return nameHash;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    public Set<AccountModel> getAccounts() {
        return accounts;
    }
}
