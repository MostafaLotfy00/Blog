package com.example.Blog.service;

import com.example.Blog.dto.GetUsersDTO;
import com.example.Blog.dto.PostDTO;
import com.example.Blog.dto.UserDTO;
import com.example.Blog.dto.UserDetailsDTO;
import com.example.Blog.entity.Post;
import com.example.Blog.entity.User;
import com.example.Blog.entity.UserDetails;
import com.example.Blog.exception.ResourceNotFoundException;
import com.example.Blog.repository.UserDetailsRepo;
import com.example.Blog.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<GetUsersDTO> getAll(){
        List<GetUsersDTO> users= this.userRepository.findAll().stream().map(user -> modelMapper.map(user, GetUsersDTO.class)).collect(Collectors.toList());
        return users;
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
