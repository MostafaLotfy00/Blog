package com.example.Blog.controller;

import com.example.Blog.dto.CommentDTO;
import com.example.Blog.dto.GenericResponse;
import com.example.Blog.dto.PostDTO;
import com.example.Blog.entity.Comment;
import com.example.Blog.entity.Post;
import com.example.Blog.service.CommentService;
import com.example.Blog.service.PostService;
import com.example.Blog.utils.AppConstants;
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
    private final CommentService commentService;
    @Autowired
    public PostController(PostService postService, CommentService commentService){
        this.postService=postService;
        this.commentService= commentService;
    }

    @GetMapping
    public GenericResponse<PostDTO> getAll(@RequestParam(value = "pageNo" , defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                           @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                           @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORTING, required = false) String sortBy,
                                           @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_ORDER, required = false) String sortDir){
        return this.postService.getAll(pageNo, pageSize, sortBy, sortDir);
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
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable(value = "postId") Long id, @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(this.postService.addComment(id, commentDTO), HttpStatus.CREATED);
    }
    @GetMapping("/{postId}/comments")
    public List<Comment> getComments(@PathVariable(value = "postId") Long id){
        return this.commentService.findByPostId(id);
    }

}
