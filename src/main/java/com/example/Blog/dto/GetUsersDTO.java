package com.example.Blog.dto;

import com.example.Blog.entity.Post;
import com.example.Blog.entity.UserDetails;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUsersDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserDetails userDetails;
    private List<Post> posts;
}
