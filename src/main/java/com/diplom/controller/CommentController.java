package com.diplom.controller;

import com.diplom.controller.dto.CommentDto;
import com.diplom.model.Comment;
import com.diplom.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/new")
    public String newComment(Model model) {
        model.addAttribute("comment", new Comment());
        return "comment/new";
    }

    @PostMapping
    public String create(@ModelAttribute("comment") CommentDto commentDto,
                         @RequestParam(value = "file", required = false) MultipartFile file,
                         Principal principal) {
        commentService.saveComment(file, commentDto, principal.getName());
        return "redirect:/profile";
    }
}
