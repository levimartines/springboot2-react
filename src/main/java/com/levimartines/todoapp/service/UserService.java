package com.levimartines.todoapp.service;

import com.levimartines.todoapp.exceptions.InvalidRequestException;
import com.levimartines.todoapp.exceptions.ObjectNotFoundException;
import com.levimartines.todoapp.model.User;
import com.levimartines.todoapp.repository.UserRepository;
import com.levimartines.todoapp.service.auth.CustomUserDetails;
import com.levimartines.todoapp.util.JWTUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder pe;

    public List<User> getAll() {
        CustomUserDetails authUser = JWTUtils.authenticated();
        if (authUser != null) {
            return repository.findAll();
        }
        return new ArrayList<>();
    }

    public User findById(Long id) {
        Optional<User> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new ObjectNotFoundException("Objeto nao encontrado. Id: " + id);
        }
        return optional.get();
    }

    public void update(Long id, String email) {
        User todo = findById(id);
        todo.setEmail(email);
        repository.save(todo);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }


    public User add(User userBean) {
        User user = repository.findByLoginIgnoreCase(userBean.getLogin());
        if (user != null) {
            throw new InvalidRequestException("Login or email already exist");
        }
        userBean.setAdmin(false);
        userBean.setId(null);
        userBean.setPassword(pe.encode(userBean.getPassword()));
        repository.save(userBean);
        return userBean;
    }
}
