package com.levimartines.todoapp.controller;

import com.levimartines.todoapp.model.Post;
import com.levimartines.todoapp.service.PostService;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PostController {

    private final PostService service;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Returns all Posts of a User")
    public ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Find Post by ID")
    public ResponseEntity<Post> getPost(@PathVariable String id) {
        Post post = service.findById(Long.parseLong(id));
        return ResponseEntity.ok(post);
    }

    @PostMapping
    @ApiOperation(value = "Create a Post")
    public ResponseEntity<Post> addPost(@RequestBody Post Post) throws URISyntaxException {
        service.add(Post);
        return ResponseEntity.created(new URI(Post.getId().toString())).build();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a Post")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody String email) {
        service.update(Long.parseLong(id), email);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Delete a Post")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}
