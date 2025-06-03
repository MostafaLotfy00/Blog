package com.example.Blog.repository;

import com.example.Blog.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<UserDetails, Long> {
}
