package com.example.Blog.dto;

import lombok.Data;

import java.util.List;

@Data
public class GenericResponse<T> {
    private List<T> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
