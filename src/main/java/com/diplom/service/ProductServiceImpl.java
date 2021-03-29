package com.diplom.service;

import com.diplom.Exceptions.BusinessException;
import com.diplom.controller.dto.ProductDto;
import com.diplom.repository.ProductRepository;
import com.diplom.utils.ProductConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.diplom.utils.ProductConverter.convertProductDtoToEntity;
import static com.diplom.utils.ProductConverter.convertProductEntityToDto;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getAllProduct() {
        return productRepository.findAll()
                .stream()
                .map(ProductConverter::convertProductEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProduct(int id) {
        return convertProductEntityToDto(productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Не удалось найти продукт")));
    }

    @Override
    public List<ProductDto> getProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(ProductConverter::convertProductEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveProduct(ProductDto productDto) {

        productRepository.save(convertProductDtoToEntity(productDto));
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public void updateProduct(ProductDto productDto) {
        productRepository.save(convertProductDtoToEntity(productDto));
    }
}

