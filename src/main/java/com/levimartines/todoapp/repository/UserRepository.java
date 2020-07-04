package com.levimartines.todoapp.repository;

import com.levimartines.todoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginIgnoreCase(String login);
}
