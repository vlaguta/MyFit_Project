package com.diplom.repository;

import com.diplom.model.Eating;
import com.diplom.model.ProductDailyMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDailyMenuRepository extends JpaRepository<ProductDailyMenu, Integer> {

    List<ProductDailyMenu> findAllProductDailyMenuByDailyMenuId(int daily);

    Optional<ProductDailyMenu> findByDailyMenuIdAndEatingAndProductId(int daily, Eating eating, int productId);

}
