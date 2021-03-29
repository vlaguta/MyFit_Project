package com.diplom.controller;

import com.diplom.controller.dto.PostDto;
import com.diplom.model.Post;
import com.diplom.service.PostService;
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
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping("/new")
    public String newPost(Model model) {
        model.addAttribute("post", new Post());
        return "posts/new";
    }

    @PostMapping
    public String create(@ModelAttribute("post") PostDto postDto,
                         @RequestParam(value = "file", required = false) MultipartFile file,
                         Principal principal) {
        postService.savePost(file, postDto, principal.getName());
        return "redirect:/profile";
    }
}
