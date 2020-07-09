package com.levimartines.todoapp.repository;

import com.levimartines.todoapp.model.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserId(Long userId);

}
