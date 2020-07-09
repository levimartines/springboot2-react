package com.levimartines.todoapp.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.levimartines.todoapp.bean.UserBean;
import com.levimartines.todoapp.model.User;
import com.levimartines.todoapp.service.UserService;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
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
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {

    private final UserService service;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Returns all Users")
    public ResponseEntity<MappingJacksonValue> getAll() {

        List<User> userList = service.getAll();
        List<UserBean> beanList = userList.stream().map(UserBean::new)
            .collect(Collectors.toList());

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
            .filterOutAllExcept("login", "email");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(beanList);
        mapping.setFilters(filters);

        return ResponseEntity.ok(mapping);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Find User by ID")
    public EntityModel<User> getUser(@PathVariable String id) {
        User user = service.findById(Long.parseLong(id));

        EntityModel<User> resource = EntityModel.of(user);
        WebMvcLinkBuilder linkTo =
            linkTo(methodOn(this.getClass()).getAll());

        resource.add(linkTo.withRel("all-users"));

        //HATEOAS

        return resource;
    }

    @PostMapping
    @ApiOperation(value = "Create a User")
    public ResponseEntity<User> addUser(@RequestBody User User) throws URISyntaxException {
        service.add(User);
        return ResponseEntity.created(new URI(User.getId().toString())).build();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a User")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody String email) {
        service.update(Long.parseLong(id), email);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Delete a User")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}
