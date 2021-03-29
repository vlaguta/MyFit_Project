package com.diplom.service;

import com.diplom.controller.dto.CommentDto;
import com.diplom.model.Comment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommentService {

    void saveComment(MultipartFile file, CommentDto commentDto, String login);

    List<Comment> getAllComment();

}
