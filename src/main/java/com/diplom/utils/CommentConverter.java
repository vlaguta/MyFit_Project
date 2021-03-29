package com.diplom.utils;

import com.diplom.controller.dto.CommentDto;
import com.diplom.model.Comment;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommentConverter {

    public static CommentDto convertCommentEntityToCommentDto(Comment comment) {

        return CommentDto.builder()
                .text(comment.getText())
                .customerDto(CustomerConverter.convertCustomerEntityToCustomerDto(comment.getCustomer()))
                .photo(comment.getPhoto())
                .createdDate(comment.getCreatedDate())
                .build();
    }

    public static Comment convertCommentDtoToCommentEntity(CommentDto commentDto) {
        return Comment.builder()
                .text(commentDto.getText())
                .customer(CustomerConverter.convertCustomerDtoToCustomerEntity(commentDto.getCustomerDto()))
                .photo(commentDto.getPhoto())
                .createdDate(commentDto.getCreatedDate())
                .build();
    }
}
