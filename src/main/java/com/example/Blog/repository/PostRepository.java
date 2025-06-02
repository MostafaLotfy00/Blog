package com.example.Blog.repository;

import com.example.Blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByTitle(String title);

//    @Query("SELECT p FROM Post p WHERE p.title = :title AND p.id <> :id")
//    List<Post> findByTitleAndNotId(@Param("title") String title, @Param("id") Long id);
    boolean existsByTitleAndIdNot(String title, Long id);
}
