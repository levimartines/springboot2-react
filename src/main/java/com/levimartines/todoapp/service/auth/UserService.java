package com.levimartines.todoapp.service.auth;

import com.levimartines.todoapp.exceptions.AuthorizationException;
import com.levimartines.todoapp.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    public static CustomUserDetails authenticated() {
        try {
            return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public static Long getAuthUserId() {
        CustomUserDetails authUser = authenticated();
        if (authUser != null) {
            return authUser.getId();
        }
        throw new AuthorizationException("Acesso proibido.");
    }

    public static boolean isOwner(Long userId) {
        CustomUserDetails authUser = authenticated();
        if (authUser == null || !userId.equals(authUser.getId())) {
            return false;
        }
        return true;
    }
}
