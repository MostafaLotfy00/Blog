package com.example.Blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDataDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
