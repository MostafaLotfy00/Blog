package com.example.Blog.dto;

import com.example.Blog.entity.Post;
import com.example.Blog.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
public class PostDTO {

    @Valid
    private Long id;
    @NotNull(message = "title field is required")
    @NotBlank(message = "Title can't be null")
    @Size(min = 2, max = 10)
    private String title;
    private String description;
    private String content;
    private UserDataDTO userDTO;
    public PostDTO(Post post){
        this.id= post.getId();
        this.title= post.getTitle();
        this.description= post.getDescription();
        this.content= post.getContent();
        this.userDTO = post.getUser() != null ? new UserDataDTO(post.getUser()) : null;

    }
}
