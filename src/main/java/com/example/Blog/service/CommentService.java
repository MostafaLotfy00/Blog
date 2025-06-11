package com.example.Blog.service;

import com.example.Blog.entity.Comment;
import com.example.Blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository=commentRepository;
    }

    public List<Comment> findByPostId(Long id){
        return this.commentRepository.findByPostId(id);
    }
}
