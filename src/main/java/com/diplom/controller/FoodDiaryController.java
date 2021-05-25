package com.diplom.controller;

import com.diplom.controller.dto.CustomerDto;
import com.diplom.service.CustomerService;
import com.diplom.service.DailyMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/food-diaries")
@RequiredArgsConstructor
public class FoodDiaryController {

    private final DailyMenuService dailyMenuService;
    private final CustomerService customerService;

    @GetMapping
    public String getAllDailyMenus(Model model, Principal principal) {
        String login = principal.getName();
        CustomerDto customer = customerService.getCustomer(login);
        model.addAttribute("foodDiary", dailyMenuService.getAllDailyMenus(customer.getId()));
        return "foodDiary/foodDiary";
    }
}
