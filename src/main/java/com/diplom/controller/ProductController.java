package com.diplom.controller;

import com.diplom.controller.dto.ProductDto;
import com.diplom.model.Product;
import com.diplom.service.DailyMenuService;
import com.diplom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final DailyMenuService dailyMenuService;

    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "products/getProducts";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "products/new";
    }

    @GetMapping("/search")
    public String getProduct(@RequestParam(value = "name") String name,
                             @RequestParam(value = "eating") String eating,
                             Model model,
                             Principal principal) {
        model.addAttribute("products", productService.getProducts(name));
        model.addAttribute("dailyMenu", dailyMenuService.getDailyMenuDto(principal.getName()));
        model.addAttribute("eating", eating);
        return "dailyMenu/addProductToDailyMenu";
    }

    @PostMapping
    public String create(@ModelAttribute("product") @Valid ProductDto productDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "products/new";
        }
        productService.saveProduct(productDto);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("product", productService.getProduct(id));
        return "products/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("product") ProductDto productDto) {
        productService.updateProduct(productDto);
        return "redirect:/products";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
