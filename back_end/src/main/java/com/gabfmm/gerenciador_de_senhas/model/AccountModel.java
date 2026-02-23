package com.gabfmm.gerenciador_de_senhas.model;

import com.gabfmm.gerenciador_de_senhas.converter.CryptographyConverter;
import com.gabfmm.gerenciador_de_senhas.converter.HashConverter;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "Accounts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_title_user_id",
                        columnNames = {"titleHash", "user_id"}
                )
        }
)
public class AccountModel {

    // -- Fields --

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 512)
    @Convert(converter = CryptographyConverter.class)
    private String titleEncrypted;

    @Column(nullable = false, length = 100)
    @Convert(converter = HashConverter.class)
    private String titleHash;

    @Column(length = 512)
    @Convert(converter = CryptographyConverter.class)
    private String descriptionEncrypted;

    @Column(nullable = false, length = 512)
    @Convert(converter = CryptographyConverter.class)
    private String passwordEncrypted;

    @ManyToOne
    @JoinColumn(name = "user_id") // it already references Users.id
    private UserModel user;

    // -- Methods --

    public AccountModel(){}

    public void setTitleHash(final String titleHash){
        this.titleHash = titleHash;
    }

    public void setTitleEncrypted(String titleEncrypted) {
        this.titleEncrypted = titleEncrypted;
    }

    public void setDescriptionEncrypted(final String descriptionEncrypted){
        this.descriptionEncrypted = descriptionEncrypted;
    }

    public void setPasswordEncrypted(final String passwordEncrypted){
        this.passwordEncrypted = passwordEncrypted;
    }

    public void setUser(final UserModel userModel){
        this.user = userModel;
    }

    public Long getId(){
        return id;
    }

    public String getTitleHash(){
        return titleHash;
    }

    public String getTitle() {
        return titleEncrypted;
    }

    public String getDescription() {
        return descriptionEncrypted;
    }

    public String getPassword() {
        return passwordEncrypted;
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountModel that)) return false;
        return Objects.equals(titleHash, that.titleHash) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titleHash, user);
    }
}