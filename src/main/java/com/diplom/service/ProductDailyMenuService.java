package com.diplom.service;

import com.diplom.model.Eating;
import com.diplom.model.ProductDailyMenu;

import java.util.List;

public interface ProductDailyMenuService {

    List<ProductDailyMenu> getProductDailyMenus(int dailyId);

    void save(ProductDailyMenu productDailyMenu);

    ProductDailyMenu getProductDailyMenus(int dailyId, Eating eating, int productId);
}
