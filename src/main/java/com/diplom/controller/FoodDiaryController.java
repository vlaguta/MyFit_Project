package com.diplom.controller;

import com.diplom.service.DailyMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/food-diaries")
@RequiredArgsConstructor
public class FoodDiaryController {

    private final DailyMenuService dailyMenuService;

    @GetMapping
    public String getAllDailyMenus(Model model) {
        model.addAttribute("foodDiary", dailyMenuService.getAllDailyMenus());
        return "foodDiary/foodDiary";
    }
}
