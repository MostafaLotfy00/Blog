package com.example.Blog.service;

import com.example.Blog.dto.CommentDTO;
import com.example.Blog.dto.GenericResponse;
import com.example.Blog.dto.PostDTO;
import com.example.Blog.entity.Comment;
import com.example.Blog.entity.Post;
import com.example.Blog.entity.User;
import com.example.Blog.exception.ResourceNotFoundException;
import com.example.Blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private ModelMapper modelMapper;
    @Autowired
    public PostService(PostRepository postRepository, ModelMapper modelMapper){
        this.postRepository = postRepository;
        this.modelMapper= modelMapper;
    }

    public GenericResponse<PostDTO> getAll(int pageNo, int pageSize, String sortBy, String sortDir)
    {
        Sort sort= sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postPages= this.postRepository.findAll(pageable);
        List<Post> posts= postPages.getContent();
        List<PostDTO> postsDTO= posts.stream().map(post -> new PostDTO(post)).collect(Collectors.toList());
        GenericResponse<PostDTO> postResponse= new GenericResponse<>();
        postResponse.setContent(postsDTO);
        postResponse.setPageNo(postPages.getNumber());
        postResponse.setPageSize(postPages.getSize());
        postResponse.setTotalPages(postPages.getTotalPages());
        postResponse.setTotalElements(postPages.getTotalElements());
        postResponse.setLast(postPages.isLast());
        return postResponse;
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

    public Comment addComment(Long id,CommentDTO commentDTO){
        Post post= this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no posts with id: "+ id));
        Comment comment= this.modelMapper.map(commentDTO, Comment.class);
        post.addComment(comment);
        this.postRepository.save(post);
        return comment;
    }
}
