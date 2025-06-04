package com.example.Blog.service;

import com.example.Blog.dto.PostDTO;
import com.example.Blog.entity.Post;
import com.example.Blog.exception.ResourceNotFoundException;
import com.example.Blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    @Autowired
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public List<PostDTO> getAll()
    {
        List<PostDTO> posts= new ArrayList<>();
        for(Post post: this.postRepository.findAll()){
            posts.add(new PostDTO(post));
        }
        return posts;
    }

    public PostDTO getOne(Long id){
        Post post= this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no posts with id : "+ id));
        return new PostDTO(post);
    }

    public Post save(PostDTO postDTO){
        Post existingPost= this.postRepository.findByTitle(postDTO.getTitle());
        if(existingPost != null){
            throw new IllegalArgumentException("Title Already Exist");
        }
        Post post= new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        return this.postRepository.save(post);
    }

    public PostDTO update(Long id, PostDTO postDTO){
        Post post= this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no posts with id : "+ id));
         boolean exist = this.postRepository.existsByTitleAndIdNot(postDTO.getTitle(), id);
        System.out.println(exist);
        if(exist){
            throw new IllegalArgumentException("title field should be unique");
        }
        post.setContent(postDTO.getContent());
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        this.postRepository.save(post);
        return new PostDTO(post);
    }
}
