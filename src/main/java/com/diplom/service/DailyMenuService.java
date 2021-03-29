package com.diplom.service;

import com.diplom.controller.dto.DailyMenuDto;
import com.diplom.controller.dto.ProductDto;
import com.diplom.model.Eating;
import com.diplom.model.Customer;
import com.diplom.model.Product;

import java.util.List;
import java.util.function.Function;

public interface DailyMenuService {
    DailyMenuDto getDailyMenuDto(String login);

    List<ProductDto> getEatingProducts(List<Product> products, int dailyMenuId, Eating eating);

    int getGeneralNutrients(List<ProductDto> products, Function<ProductDto, Integer> getter);

    void saveDailyMenuForEveryCustomer();

    void saveDailyMenu(Customer customer);

    void addProductToDailyMenu(int dailyMenuId, ProductDto productDto, Eating eating);

    List<DailyMenuDto> getAllDailyMenus();
}
