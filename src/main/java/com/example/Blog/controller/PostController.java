package com.example.Blog.controller;

import com.example.Blog.dto.PostDTO;
import com.example.Blog.entity.Post;
import com.example.Blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    @Autowired
    public PostController(PostService postService){
        this.postService=postService;
    }

    @GetMapping
    public List<PostDTO> getAll(){
        return this.postService.getAll();
    }
    @GetMapping("/{id}")
    public PostDTO getOne(@PathVariable(name = "id") Long id){
        return this.postService.getOne(id);
    }
    @PostMapping
    public ResponseEntity<Post> save(@Valid @RequestBody  PostDTO postDTO){
        Post savedPost= this.postService.save(postDTO);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> update(@PathVariable(name = "id") Long id, @Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(this.postService.update(id, postDTO), HttpStatus.OK);
    }

}
