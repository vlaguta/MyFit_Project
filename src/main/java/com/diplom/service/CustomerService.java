package com.diplom.service;

import com.diplom.controller.dto.CustomerDto;
import com.diplom.controller.dto.CustomerRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface CustomerService extends UserDetailsService {

    List<CustomerDto> getAllCustomer();

    boolean saveCustomer(CustomerRegistrationDto customerRegistrationDto);

    void updateCustomer(int id, CustomerDto customerDto);

    CustomerDto getCustomer(String login);

    CustomerDto getCustomer(int id);

}
