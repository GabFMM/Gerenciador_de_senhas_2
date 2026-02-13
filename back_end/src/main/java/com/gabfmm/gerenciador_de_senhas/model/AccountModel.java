package com.gabfmm.gerenciador_de_senhas.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "Accounts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_title_user_id",
                        columnNames = {"title", "user_id"}
                )
        }
)
public class AccountModel {

    // -- Fields --

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = false)
    private String title;

    @Column(nullable = true, unique = false)
    private String description;

    @Column(nullable = false, unique = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_id") // it already references Users.id
    private UserModel user;

    // -- Methods --

    public AccountModel(){}

    public void setTitle(final String title){
        this.title = title;
    }

    public void setDescription(final String description){
        this.description = description;
    }

    public void setPassword(final String password){
        this.password = password;
    }

    public void setUser(final UserModel userModel){
        this.user = userModel;
    }

    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPassword() {
        return password;
    }

    public UserModel getUser() {
        return user;
    }
}