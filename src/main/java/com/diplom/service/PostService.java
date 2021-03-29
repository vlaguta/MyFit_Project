package com.diplom.service;

import com.diplom.controller.dto.PostDto;
import com.diplom.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    void savePost(MultipartFile file, PostDto postDto, String login);

    List<Post> getAllPosts();

}
