package com.diplom.service;

import com.diplom.Exceptions.BusinessException;
import com.diplom.controller.dto.DailyMenuDto;
import com.diplom.controller.dto.ProductDto;
import com.diplom.model.Customer;
import com.diplom.model.DailyMenu;
import com.diplom.model.Eating;
import com.diplom.model.Product;
import com.diplom.model.ProductDailyMenu;
import com.diplom.repository.CustomerRepository;
import com.diplom.repository.DailyMenuRepository;
import com.diplom.utils.DailyMenuConverter;
import com.diplom.utils.ProductConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

@Service
@RequiredArgsConstructor
public class DailyMenuServiceImpl implements DailyMenuService {

    private final DailyMenuRepository dailyMenuRepository;
    private final CustomerRepository customerRepository;
    private final ProductDailyMenuService productDailyMenuService;

    @Override
    public DailyMenuDto getDailyMenuDto(String login) {

        DailyMenu dailyMenu = dailyMenuRepository.findByCustomerLogin(login /* LocalDate.now()*/)
                .orElseThrow(() -> new BusinessException("Не удалось найти Дневное меню"));

        Map<Eating, List<Product>> productByEating = getProductsByEating(dailyMenu);

        List<ProductDto> breakfastsProducts = getEatingProducts(productByEating.get(Eating.BREAKFAST), dailyMenu.getId(), Eating.BREAKFAST);
        List<ProductDto> dinnerProducts = getEatingProducts(productByEating.get(Eating.DINNER), dailyMenu.getId(), Eating.DINNER);
        List<ProductDto> supperProducts = getEatingProducts(productByEating.get(Eating.SUPPER), dailyMenu.getId(), Eating.SUPPER);

        DailyMenuDto dailyMenuDto = DailyMenuConverter.convertDailyMenuEntityToDailyMenuDto(dailyMenu);
        dailyMenuDto.setBreakfast(breakfastsProducts);
        dailyMenuDto.setDinner(dinnerProducts);
        dailyMenuDto.setSupper(supperProducts);

        return dailyMenuDto;
    }

    @Override
    public List<ProductDto> getEatingProducts(List<Product> products, int dailyMenuId, Eating eating) {

        return Optional.ofNullable(products)
                .stream()
                .flatMap(List::stream)
                .map(ProductConverter::convertProductEntityToDto)
                .map(productDto -> setFactualNutrientsForProductDto(dailyMenuId, eating, productDto))
                .collect(Collectors.toList());
    }

    @Override
    public int getGeneralNutrients(List<ProductDto> products, Function<ProductDto, Integer> getter) {

        return products.stream()
                .map(getter::apply)
                .flatMapToInt(IntStream::of)
                .sum();
    }

    @Override
    public void saveDailyMenuForEveryCustomer() {

        customerRepository.findAll()
                .forEach(this::saveDailyMenu);
    }

    @Override
    public void saveDailyMenu(Customer customer) {

        DailyMenu dailyMenu = new DailyMenu();
        dailyMenu.setCreatedDate(LocalDate.now());
        dailyMenu.setCustomer(customer);
        dailyMenuRepository.save(dailyMenu);
    }

    @Override
    public void addProductToDailyMenu(int dailyMenuId, ProductDto productDto, Eating eating) {

        ProductDailyMenu productDailyMenuFromDb = productDailyMenuService.getProductDailyMenus(dailyMenuId, eating, productDto.getId());

        int weight = productDailyMenuFromDb.getProductWeight() + productDto.getWeight();
        productDailyMenuFromDb.setProductWeight(weight);
        productDailyMenuService.save(productDailyMenuFromDb);

        DailyMenu dailyMenu = dailyMenuRepository.findById(dailyMenuId)
                .orElseThrow(() -> new BusinessException("Не удалось найти Дневное меню"));

        Map<Eating, List<Product>> productByEating = getProductsByEating(dailyMenu);

        List<ProductDto> breakfastsProducts = getEatingProducts(productByEating.get(Eating.BREAKFAST), dailyMenu.getId(), Eating.BREAKFAST);
        List<ProductDto> dinnerProducts = getEatingProducts(productByEating.get(Eating.DINNER), dailyMenu.getId(), Eating.DINNER);
        List<ProductDto> supperProducts = getEatingProducts(productByEating.get(Eating.SUPPER), dailyMenu.getId(), Eating.SUPPER);

        dailyMenu.setGeneralCalories(getGeneralNutrients(breakfastsProducts, ProductDto::getFactualCalories)
                + getGeneralNutrients(dinnerProducts, ProductDto::getFactualCalories)
                + getGeneralNutrients(supperProducts, ProductDto::getFactualCalories));
        dailyMenu.setGeneralProteins(getGeneralNutrients(breakfastsProducts, ProductDto::getFactualProtein)
                + getGeneralNutrients(dinnerProducts, ProductDto::getFactualProtein)
                + getGeneralNutrients(supperProducts, ProductDto::getFactualProtein));
        dailyMenu.setGeneralFats(getGeneralNutrients(breakfastsProducts, ProductDto::getFactualFat)
                + getGeneralNutrients(dinnerProducts, ProductDto::getFactualFat)
                + getGeneralNutrients(supperProducts, ProductDto::getFactualFat));
        dailyMenu.setGeneralCarbonhydrates(getGeneralNutrients(breakfastsProducts, ProductDto::getCarbonhydrates)
                + getGeneralNutrients(dinnerProducts, ProductDto::getCarbonhydrates)
                + getGeneralNutrients(supperProducts, ProductDto::getCarbonhydrates));
        dailyMenuRepository.save(dailyMenu);

    }

    @Override
    public List<DailyMenuDto> getAllDailyMenus() {

        return dailyMenuRepository.findAll()
                .stream()
                .map(DailyMenuConverter::convertDailyMenuEntityToDailyMenuDto)
                .collect(Collectors.toList());
    }

    private Map<Eating, List<Product>> getProductsByEating(DailyMenu dailyMenu) {

        List<ProductDailyMenu> productDailyMenus = productDailyMenuService.getProductDailyMenus(dailyMenu.getId());

        return productDailyMenus.stream()
                .collect(groupingBy(ProductDailyMenu::getEating,
                        mapping(ProductDailyMenu::getProduct, Collectors.toList())));
    }

    private ProductDto setFactualNutrientsForProductDto(int dailyMenuId, Eating eating, ProductDto productDto) {
        ProductDailyMenu productDailyMenu = productDailyMenuService.getProductDailyMenus(dailyMenuId, eating, productDto.getId());
        productDto.setFactualCalories(productDto.getNominalCalories() * productDailyMenu.getProductWeight() / 100);
        productDto.setFactualCarbonhydrates(productDto.getCarbonhydrates() * productDailyMenu.getProductWeight() / 100);
        productDto.setFactualProtein(productDto.getProtein() * productDailyMenu.getProductWeight() / 100);
        productDto.setFactualFat(productDto.getFat() * productDailyMenu.getProductWeight() / 100);
        return productDto;
    }

    private List<ProductDto> getDailyMenuProducts(Map<Eating, List<Product>> productsByEating) {
        return productsByEating.values()
                .stream()
                .flatMap(List::stream)
                .map(ProductConverter::convertProductEntityToDto)
                .collect(Collectors.toList());
    }
}
