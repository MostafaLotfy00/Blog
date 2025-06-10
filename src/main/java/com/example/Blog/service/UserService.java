package com.example.Blog.service;

import com.example.Blog.dto.*;
import com.example.Blog.entity.Post;
import com.example.Blog.entity.User;
import com.example.Blog.entity.UserDetails;
import com.example.Blog.exception.ResourceNotFoundException;
import com.example.Blog.repository.UserDetailsRepo;
import com.example.Blog.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserDetailsRepo userDetailsRepo;
    private ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserDetailsRepo userDetailsRepo, ModelMapper modelMapper){
        this.userRepository= userRepository;
        this.userDetailsRepo= userDetailsRepo;
        this.modelMapper= modelMapper;
    }

    public GenericResponse<GetUsersDTO> getAll(int pageNo, int pageSize, String sortBy, String sortDir){
        Sort sort= sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo, pageSize, sort);
        Page<User> usersPages= this.userRepository.findAll(pageable);
        List<GetUsersDTO> users= usersPages.stream().map(user -> modelMapper.map(user, GetUsersDTO.class)).collect(Collectors.toList());
        GenericResponse<GetUsersDTO> genericResponse= new GenericResponse<>();
        genericResponse.setContent(users);
        genericResponse.setPageNo(usersPages.getNumber());
        genericResponse.setPageSize(usersPages.getSize());
        genericResponse.setTotalPages(usersPages.getTotalPages());
        genericResponse.setTotalElements(usersPages.getTotalElements());
        genericResponse.setLast(usersPages.isLast());
        return genericResponse;
    }
    public User saveUserDetails(Long id, UserDetailsDTO userDetailsDTO){
        UserDetails userDetails= new UserDetails();
        User user= this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no users with id: "+ id));
        userDetails.setHobby(userDetailsDTO.getHobby());
        user.addDetails(userDetails);
        return this.userRepository.save(user);
    }

    public User upateUserDetails(Long id, UserDetailsDTO userDetailsDTO){
        User user= this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no users with id: "+ id));
        user.getUserDetails().setHobby(userDetailsDTO.getHobby());
        return this.userRepository.save(user);
    }


    public User save(UserDTO userDTO){
        String email= userDTO.getEmail();
        User tempUser= this.userRepository.findByEmail(email);
        if(tempUser != null){
            throw new IllegalArgumentException("The email : "+ email + " is already exist");
        }
        User user= modelMapper.map(userDTO, User.class);
//        user.setFirstName(userDTO.getFirstName());
//        user.setLastName(userDTO.getLastName());
//        user.setEmail(userDTO.getEmail());
//        user.setUserDetails(userDTO.getUserDetails());
        return this.userRepository.save(user);
    }

    public User addPost(Long id, PostDTO postDTO){
        User user= this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no users with id: "+ id));
        Post post= modelMapper.map(postDTO, Post.class);
//        post.setTitle(postDTO.getTitle());
//        post.setContent(postDTO.getContent());
//        post.setDescription(postDTO.getDescription());
        user.addPost(post);
        return this.userRepository.save(user);
    }
}
