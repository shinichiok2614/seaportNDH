package com.example.demo.services;

import com.example.demo.dtos.PostDTO;
import com.example.demo.dtos.PostImageDTO;
import com.example.demo.exceptions.DataNotFoundException;
import com.example.demo.exceptions.InvalidParamException;
import com.example.demo.models.Post;
import com.example.demo.models.PostImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IPostService {
    public Post createPost(PostDTO postDTO) throws DataNotFoundException;

    Post getPostById(long id) throws DataNotFoundException;

    Page<Post> getAllPost(PageRequest pageRequest);

    Post updatePost(long id, PostDTO postDTO) throws DataNotFoundException;

    void deletePost(long id) throws DataNotFoundException;

    boolean existsByName(String name);

    PostImage createPostImage(Long id, PostImageDTO postImageDTO) throws InvalidParamException, DataNotFoundException;
}
