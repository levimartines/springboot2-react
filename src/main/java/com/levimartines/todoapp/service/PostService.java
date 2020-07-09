package com.levimartines.todoapp.service;

import com.levimartines.todoapp.exceptions.AuthorizationException;
import com.levimartines.todoapp.exceptions.ObjectNotFoundException;
import com.levimartines.todoapp.model.Post;
import com.levimartines.todoapp.repository.PostRepository;
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
public class PostService {

    private final PostRepository repository;

    public List<Post> getAll() {
        CustomUserDetails authUser = JWTUtils.authenticated();
        if (authUser != null) {
            return repository.findAllByUserId(authUser.getId());
        }
        return new ArrayList<>();
    }

    public Post findById(Long id) {
        Optional<Post> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new ObjectNotFoundException("Objeto nao encontrado. Id: " + id);
        }
        Post todo = optional.get();
        if (!JWTUtils.isOwner(todo.getUserId())) {
            throw new AuthorizationException("Acesso proibido.");
        }
        return todo;
    }

    public void update(Long id, String newDescription) {
        Post post = findById(id);
        post.setDescription(newDescription);
        repository.save(post);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }


    public Post add(Post todo) {
        todo.setId(null);
        todo.setUserId(JWTUtils.getId());
        repository.save(todo);
        return todo;
    }
}
