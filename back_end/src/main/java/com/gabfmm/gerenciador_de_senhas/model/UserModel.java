package com.gabfmm.gerenciador_de_senhas.model;

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

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = false)
    private String password;

    // -- Attributes --

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<AccountModel> accounts;

    // -- Methods --

    public UserModel() {
        accounts = new HashSet<>();
    }

    public void setName(final String name){
        this.name = name;
    }

    public void setPassword(final String password){
        this.password = password;
    }

    public void setAccounts(final Set<AccountModel> accounts) {
        this.accounts = accounts;
    }

    Long getId(){
        return id;
    }

    String getName(){
        return name;
    }

    String getPassword(){
        return password;
    }

    public Set<AccountModel> getAccounts() {
        return accounts;
    }
}
