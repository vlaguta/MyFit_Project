package com.diplom.controller;

import com.diplom.controller.dto.ProductDto;
import com.diplom.model.Eating;
import com.diplom.service.DailyMenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class DailyMenuController {

    private final DailyMenuService dailyMenuService;

    @GetMapping("/daily-menus")
    public String getDailyMenuById(Model model, Principal principal) {
        model.addAttribute("dailyMenu", dailyMenuService.getDailyMenuDto(principal.getName()));
        return "dailyMenu/getDailyMenu";
    }

    @GetMapping("/add")
    public String getDailyMenuById(@RequestParam("eating") String eating, Model model) {
        model.addAttribute("eating", eating);

        return "dailyMenu/addProductToDailyMenu";
    }

    @PostMapping("/daily-menus/{dailyMenuId}/products")
    public String addDailyMenu(@ModelAttribute("product") ProductDto product,
                               @PathVariable("dailyMenuId") Integer dailyMenuId) {
        dailyMenuService.addProductToDailyMenu(dailyMenuId, product, Eating.valueOf(product.getEating()));
        return "redirect:/daily-menus";
    }
}
