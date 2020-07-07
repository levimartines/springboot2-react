package com.levimartines.todoapp.service.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
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

    public CustomUserDetails(Long id, String login, String senha, boolean isAdmin) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.authorities = buildAuthorities(isAdmin);
    }

    private Collection<? extends GrantedAuthority> buildAuthorities(boolean isAdmin) {
        List<String> list = new ArrayList<>();
        list.add("ROLE_CLIENT");
        if (isAdmin) {
            list.add("ROLE_ADMIN");
        }
        return list.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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
