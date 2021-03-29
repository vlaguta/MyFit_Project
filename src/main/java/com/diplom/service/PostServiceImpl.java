package com.diplom.service;

import com.diplom.Exceptions.BusinessException;
import com.diplom.controller.dto.PostDto;
import com.diplom.model.Post;
import com.diplom.repository.CustomerRepository;
import com.diplom.repository.PostRepository;
import com.diplom.utils.CustomerConverter;
import com.diplom.utils.PostConverter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private static final Logger LOGGER = LogManager.getLogger(PostServiceImpl.class);
    private final PostRepository postRepository;
    private final CustomerRepository customerRepository;
    private final PhotoService photoService;
    private final Path PhotoDirectoryPath = Paths.get("src/main/resources/static");

    @Override
    public void savePost(MultipartFile file, PostDto postDto, String login) {

        if (file.getSize() != 0) {
            postDto.setPhoto(photoService.save(file));
        }
        postDto.setCreatedDate(LocalDateTime.now());
        postDto.setCustomerDto(CustomerConverter.convertCustomerEntityToCustomerDto(customerRepository.findCustomerByLogin(login)
                .orElseThrow(() -> new BusinessException("Не удалось найти пользователя"))));

        postRepository.save(PostConverter.convertPostDtoToPostEntity(postDto));
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Post::getCreatedDate)
                        .reversed())
                .collect(Collectors.toList());
    }
}
