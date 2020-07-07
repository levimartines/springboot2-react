package com.levimartines.todoapp.service.auth;

import com.levimartines.todoapp.model.User;
import com.levimartines.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = repo.findByLoginIgnoreCase(login);
        if (user == null) {
            throw new UsernameNotFoundException(login);
        }
        return new CustomUserDetails(user.getId(), user.getLogin(), user.getPassword());
    }
}
