package com.levimartines.todoapp.service.auth;

import java.util.Collection;
import java.util.Collections;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Slf4j
public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String login;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String login, String senha) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
