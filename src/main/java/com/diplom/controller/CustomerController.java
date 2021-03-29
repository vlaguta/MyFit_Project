package com.diplom.controller;

import com.diplom.controller.dto.CustomerDto;
import com.diplom.service.CustomerService;
import com.diplom.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final PhotoService photoService;

    @GetMapping("/profile")
    public String userProfile(Principal principal, Model model) {
        String login = principal.getName();
        CustomerDto customer = customerService.getCustomer(login);
        model.addAttribute("user", customer);
        model.addAttribute("photo", photoService.getPhoto(customer.getId()));
        return "userProfile/userProfile";
    }

    @GetMapping("/login")
    public String LoginForm(Model model) {
        return "security/login";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", customerService.getCustomer(id));
        return "userProfile/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid CustomerDto customerDto,
                         BindingResult bindingResult,
                         @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "userProfile/edit";
        }
        customerService.updateCustomer(id, customerDto);
        return "redirect:/customers/profile";
    }
}
