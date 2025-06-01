package com.example.Blog.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
public class PostDTO {

    @Valid
    private Long id;
    @NotNull(message = "title field is required")
    @NotBlank(message = "Title can't be null")
    @Size(min = 2, max = 10)
    private String title;
    private String description;
    private String content;
}
