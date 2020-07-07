package com.levimartines.todoapp.service;

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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService {

    private final UserRepository repository;

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


    public User add(User user) {
        user.setId(null);
        repository.save(user);
        return user;
    }
}
