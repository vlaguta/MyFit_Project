package com.diplom.controller;

import com.diplom.controller.dto.CustomerRegistrationDto;
import com.diplom.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final CustomerService customerService;

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("customer", new CustomerRegistrationDto());
        return "security/registration";
    }

    @PostMapping
    public String addUser(@ModelAttribute("customer") @Valid CustomerRegistrationDto customerRegistrationDto,
                          BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {
            return "security/registration";
        }
        if (!customerService.saveCustomer(customerRegistrationDto)) {
            model.addAttribute("customerError", "Пользователь с таким именем уже существует");
            return "profile";
        }
        return "redirect:/profile";
    }
}
