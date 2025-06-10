package com.example.Blog.controller;

import com.example.Blog.dto.*;
import com.example.Blog.entity.User;
import com.example.Blog.entity.UserDetails;
import com.example.Blog.service.UserService;
import com.example.Blog.utils.AppConstants;
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
    public GenericResponse<GetUsersDTO> getALl(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                               @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                               @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORTING, required = false) String sortBy,
                                               @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_ORDER, required = false) String sortDir){
        return this.userService.getAll(pageNo, pageSize, sortBy, sortDir);
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
