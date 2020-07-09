package com.levimartines.todoapp.bean;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.levimartines.todoapp.model.User;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonFilter("UserFilter")
public class UserBean implements Serializable {

    private String login;
    private String email;
    private String password;
    private boolean admin;

    public UserBean(User user) {
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.admin = user.isAdmin();
    }
}
