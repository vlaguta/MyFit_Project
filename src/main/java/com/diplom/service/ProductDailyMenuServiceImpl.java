package com.diplom.service;

import com.diplom.model.Eating;
import com.diplom.model.ProductDailyMenu;
import com.diplom.repository.ProductDailyMenuRepository;
import com.diplom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDailyMenuServiceImpl implements ProductDailyMenuService {

    private final ProductDailyMenuRepository productDailyMenuRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ProductDailyMenu> getProductDailyMenus(int dailyId) {
        return productDailyMenuRepository.findAllProductDailyMenuByDailyMenuId(dailyId);
    }

    @Override
    public void save(ProductDailyMenu productDailyMenu) {
        productDailyMenuRepository.save(productDailyMenu);
    }

    @Override
    public ProductDailyMenu getProductDailyMenus(int dailyId, Eating eating, int productId) {
        return productDailyMenuRepository.findByDailyMenuIdAndEatingAndProductId(dailyId, eating, productId)
                .orElse(ProductDailyMenu.builder()
                        .dailyMenuId(dailyId)
                        .eating(eating)
                        .product(productRepository.findById(productId).orElse(null))
                        .build());
    }
}
