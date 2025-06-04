package com.example.Blog.dto;

import com.example.Blog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserDataDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public UserDataDTO(User user){
        this.id= user.getId();
        this.firstName= user.getFirstName();
        this.lastName= user.getLastName();
        this.email= user.getEmail();
    }
}
