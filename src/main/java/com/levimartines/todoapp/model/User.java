package com.levimartines.todoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USR_ID")
    private Long id;

    @Column(name = "USR_LOGIN")
    private String login;

    @Column(name = "USR_EMAIL")
    private String email;

    @Column(name = "USR_PASSWD")
    @JsonIgnore
    private String password;

    @Column(name = "USR_ADMIN")
    private boolean admin;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User(String login, String email, String password, boolean admin) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.posts = new ArrayList<>();
    }


}
