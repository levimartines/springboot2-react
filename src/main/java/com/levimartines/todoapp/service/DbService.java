package com.levimartines.todoapp.service;

import com.levimartines.todoapp.model.Todo;
import com.levimartines.todoapp.model.User;
import com.levimartines.todoapp.repository.TodoRepository;
import com.levimartines.todoapp.repository.UserRepository;
import java.time.LocalDate;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
@Slf4j
public class DbService {

    private final BCryptPasswordEncoder pe;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    @Value("${spring.profiles.active}")
    private String profile;

    public void instantiateTestDatabase() {
        if (!profile.equals("dev")) {
            return;
        }
        log.info("##### INSTANCIANDO BASE DE DADOS #####");
        User user = new User(null, "admin", "admin@todos.com", pe.encode("admin"), true);
        User user2 = new User(null, "levi", "levi@gmail.com", pe.encode("levi"), false);
        userRepository.saveAll(Arrays.asList(user, user2));
        int plusDays = 5;
        Todo todo1 = new Todo(null, user.getId(), "Learn React", true, LocalDate.now());
        Todo todo2 = new Todo(null, user.getId(), "Learn Spring", true,
            LocalDate.now().plusDays(plusDays));
        Todo todo3 = new Todo(null, user.getId(), "Build an App", false,
            LocalDate.now().plusDays(plusDays + 7));
        todoRepository.saveAll(Arrays.asList(todo1, todo2, todo3));
    }

}
