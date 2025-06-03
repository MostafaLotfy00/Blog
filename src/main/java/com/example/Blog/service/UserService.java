package com.example.Blog.service;

import com.example.Blog.dto.UserDTO;
import com.example.Blog.dto.UserDetailsDTO;
import com.example.Blog.entity.User;
import com.example.Blog.entity.UserDetails;
import com.example.Blog.exception.ResourceNotFoundException;
import com.example.Blog.repository.UserDetailsRepo;
import com.example.Blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserDetailsRepo userDetailsRepo;

    @Autowired
    public UserService(UserRepository userRepository, UserDetailsRepo userDetailsRepo){
        this.userRepository= userRepository;
        this.userDetailsRepo= userDetailsRepo;
    }

    public List<User> getAll(){
        return this.userRepository.findAll();
    }
    public User saveUserDetails(Long id, UserDetailsDTO userDetailsDTO){
        UserDetails userDetails= new UserDetails();
        User user= this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no users with id: "+ id));
        userDetails.setHobby(userDetailsDTO.getHobby());
        user.addDetails(userDetails);
        return this.userRepository.save(user);
    }


    public User save(UserDTO userDTO){
        String email= userDTO.getEmail();
        User tempUser= this.userRepository.findByEmail(email);
        if(tempUser != null){
            throw new IllegalArgumentException("The email : "+ email + " is already exist");
        }
        User user= new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUserDetails(userDTO.getUserDetails());
        return this.userRepository.save(user);
    }
}
