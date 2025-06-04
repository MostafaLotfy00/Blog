package com.example.Blog.controller;

import com.example.Blog.dto.PostDTO;
import com.example.Blog.dto.UserDTO;
import com.example.Blog.dto.UserDetailsDTO;
import com.example.Blog.entity.User;
import com.example.Blog.entity.UserDetails;
import com.example.Blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService= userService;
    }
    @GetMapping
    public List<User> getALl(){
        return this.userService.getAll();
    }
    @PostMapping
    public User save(@Valid @RequestBody UserDTO userDTO){
        return userService.save(userDTO);
    }
    @PostMapping("/{id}/userDetails")
    public User saveDetails(@PathVariable(name = "id") Long id, @RequestBody UserDetailsDTO userDetailsDTO){
        return this.userService.saveUserDetails(id, userDetailsDTO);
    }

    @PutMapping("/{id}/userDetails")
    public User updateDetails(@PathVariable(name = "id") Long id, @RequestBody UserDetailsDTO userDetailsDTO){
        return this.userService.upateUserDetails(id, userDetailsDTO);
    }
    @PostMapping("/{id}/posts")
    public User addPost(@PathVariable Long id, @RequestBody PostDTO postDTO){
        return this.userService.addPost(id, postDTO);
    }
}
