package com.levimartines.todoapp.controller;

import com.levimartines.todoapp.model.Todo;
import com.levimartines.todoapp.service.TodoService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TodoController {

    private final TodoService service;

    @GetMapping
    public ResponseEntity<List<Todo>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(Long.parseLong(id)));
    }

    @PostMapping
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) throws URISyntaxException {
        service.add(todo);
        return ResponseEntity.created(new URI(todo.getId().toString())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String id, @RequestBody Todo todo) {
        service.update(Long.parseLong(id), todo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}
