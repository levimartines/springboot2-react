package com.levimartines.todoapp.service;

import com.levimartines.todoapp.exceptions.AuthorizationException;
import com.levimartines.todoapp.exceptions.ObjectNotFoundException;
import com.levimartines.todoapp.model.Todo;
import com.levimartines.todoapp.repository.TodoRepository;
import com.levimartines.todoapp.security.CustomUserDetails;
import com.levimartines.todoapp.service.auth.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TodoService {

    private final TodoRepository repository;

    public List<Todo> getAll() {
        CustomUserDetails authUser = UserService.authenticated();
        if (authUser != null) {
            return repository.findAllByUserIdOrderByDueDateAsc(authUser.getId());
        }
        return new ArrayList<>();
    }

    public Todo findById(Long id) {
        Optional<Todo> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new ObjectNotFoundException("Objeto nao encontrado. Id: " + id);
        }
        Todo todo = optional.get();
        if (!UserService.isOwner(todo.getUserId())) {
            throw new AuthorizationException("Acesso proibido.");
        }
        return todo;
    }

    public void update(Long id, Todo updatedTodo) {
        Todo todo = findById(id);
        todo.setDescription(updatedTodo.getDescription());
        todo.setDueDate(updatedTodo.getDueDate());
        repository.save(todo);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }


    public Todo add(Todo todo) {
        todo.setId(null);
        todo.setUserId(UserService.getAuthUserId());
        repository.save(todo);
        return todo;
    }
}
