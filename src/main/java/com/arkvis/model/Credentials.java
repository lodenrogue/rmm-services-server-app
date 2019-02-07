package com.arkvis.model;

import javax.persistence.*;

@Entity
@Table(name = "credentials")
public class Credentials {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "salt")
    private String salt;

    @Column(name = "user_password")
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
