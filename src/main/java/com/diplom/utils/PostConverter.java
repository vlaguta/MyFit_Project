package com.diplom.utils;

import com.diplom.controller.dto.PostDto;
import com.diplom.model.Post;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PostConverter {

    public static PostDto convertPostEntityToPostDto(Post post) {

        return PostDto.builder()
                .text(post.getText())
                .customerDto(CustomerConverter.convertCustomerEntityToCustomerDto(post.getCustomer()))
                .photo(post.getPhoto())
                .createdDate(post.getCreatedDate())
                .build();
    }

    public static Post convertPostDtoToPostEntity(PostDto postDto) {
        return Post.builder()
                .text(postDto.getText())
                .customer(CustomerConverter.convertCustomerDtoToCustomerEntity(postDto.getCustomerDto()))
                .photo(postDto.getPhoto())
                .createdDate(postDto.getCreatedDate())
                .build();
    }
}
