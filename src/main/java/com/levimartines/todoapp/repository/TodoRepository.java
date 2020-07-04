package com.levimartines.todoapp.repository;

import com.levimartines.todoapp.model.Todo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByUserIdOrderByDueDateAsc(Long userId);

}
