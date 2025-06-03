package com.example.Blog.dto;

import com.example.Blog.entity.UserDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @Valid
    private Long id;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private UserDetails userDetails;
}
