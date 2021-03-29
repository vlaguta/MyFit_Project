package com.diplom.utils;

import com.diplom.controller.dto.ProductDto;
import com.diplom.model.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductConverter {

    public static ProductDto convertProductEntityToDto(Product product) {

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .nominalCalories(product.getCalories())
                .carbonhydrates(product.getCarbonhydrates())
                .fat(product.getFat())
                .protein(product.getProtein())
                .build();
    }

    public static Product convertProductDtoToEntity(ProductDto productDto) {

        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .calories(productDto.getNominalCalories())
                .carbonhydrates(productDto.getCarbonhydrates())
                .fat(productDto.getFat())
                .protein(productDto.getProtein())
                .build();
    }
}
