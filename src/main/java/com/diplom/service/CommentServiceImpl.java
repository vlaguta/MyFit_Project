package com.diplom.service;

import com.diplom.Exceptions.BusinessException;
import com.diplom.controller.dto.CommentDto;
import com.diplom.model.Comment;
import com.diplom.repository.CommentRepository;
import com.diplom.repository.CustomerRepository;
import com.diplom.utils.CommentConverter;
import com.diplom.utils.CustomerConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PhotoService photoService;
    private final CustomerRepository customerRepository;
    private final CommentRepository commentRepository;

    @Override
    public void saveComment(MultipartFile file, CommentDto commentDto, String login) {

        if (file.getSize() != 0) {
            commentDto.setPhoto(photoService.save(file));
        }
        commentDto.setCreatedDate(LocalDateTime.now());
        commentDto.setCustomerDto(CustomerConverter.convertCustomerEntityToCustomerDto(customerRepository.findCustomerByLogin(login)
                .orElseThrow(() -> new BusinessException("Не удалось найти пользователя"))));
        commentRepository.save(CommentConverter.convertCommentDtoToCommentEntity(commentDto));
    }

    @Override
    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }
}
