package com.diplom.service;

import com.diplom.controller.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProduct();

    ProductDto getProduct(int id);

    List<ProductDto> getProducts(String name);

    void saveProduct(ProductDto productDto);

    void deleteProduct(int id);

    void updateProduct(ProductDto productDto);
}

