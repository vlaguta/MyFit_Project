package com.diplom.service;

import com.diplom.Exceptions.BusinessException;
import com.diplom.controller.dto.CustomerDto;
import com.diplom.controller.dto.CustomerRegistrationDto;
import com.diplom.model.Activity;
import com.diplom.model.Sex;
import com.diplom.model.Customer;
import com.diplom.repository.CustomerRepository;
import com.diplom.repository.RoleRepository;
import com.diplom.utils.CustomerConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.diplom.utils.CoefficientConstant.AGE_COEFFICIENT;
import static com.diplom.utils.CoefficientConstant.HEIGHT_COEFFICIENT;
import static com.diplom.utils.CoefficientConstant.HIGH_ACTIVITY_COEFFICIENT;
import static com.diplom.utils.CoefficientConstant.LOW_ACTIVITY_COEFFICIENT;
import static com.diplom.utils.CoefficientConstant.MEDIUM_ACTIVITY_COEFFICIENT;
import static com.diplom.utils.CoefficientConstant.MEN_COEFFICIENT;
import static com.diplom.utils.CoefficientConstant.WEIGHT_COEFFICIENT;
import static com.diplom.utils.CoefficientConstant.WEIGHT_GAIN_COEFFICIENT;
import static com.diplom.utils.CoefficientConstant.WEIGHT_LOSS_COEFFICIENT;
import static com.diplom.utils.CoefficientConstant.WOMEN_COEFFICIENT;
import static com.diplom.utils.CustomerConverter.convertCustomerEntityToCustomerDto;
import static com.diplom.utils.CustomerConverter.convertCustomerRegistrationDtoToCustomerEntity;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public List<CustomerDto> getAllCustomer() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerConverter::convertCustomerEntityToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomer(String login) {
        CustomerDto customerDto = convertCustomerEntityToCustomerDto(customerRepository.findCustomerByLogin(login)
                .orElseThrow(() -> new BusinessException("Не удалось найти пользователя")));
        return setCaloriesForCustomerDto(customerDto);
    }

    @Override
    public CustomerDto getCustomer(int id) {
        CustomerDto customerDto = convertCustomerEntityToCustomerDto(customerRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Не удалось найти пользователя")));
        return setCaloriesForCustomerDto(customerDto);
    }

    @Override
    public boolean saveCustomer(CustomerRegistrationDto customerRegistrationDto) {

        Customer customer = convertCustomerRegistrationDtoToCustomerEntity(customerRegistrationDto);
        Customer customerFromBd = customerRepository.findCustomerByLogin(customer.getLogin())
                .orElseThrow(() -> new BusinessException("Не удалось найти пользователя"));

        if (customerFromBd != null) {
            return false;
        }

        customer.setRoles(Collections.singleton(roleRepository.findByName("user")));
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
        return true;
    }

    @Override
    @Transactional
    public void updateCustomer(int id, CustomerDto customerDto) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Невозможно обновить пользователя. Пользователь не найден"));
        if (customerDto.getSex() != null) {
            customer.setSex(customerDto.getSex());
        }
        if (customerDto.getAge() != 0) {
            customer.setAge(customerDto.getAge());
        }
        if (customerDto.getWeight() != 0) {
            customer.setWeight(customerDto.getWeight());
        }
        if (customerDto.getHeight() != 0) {
            customer.setHeight(customerDto.getHeight());
        }
        if (customerDto.getActivity() != null) {
            customer.setActivity(customerDto.getActivity());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return customerRepository.findCustomerByLogin(login)
                .orElseThrow(() -> new BusinessException("Не удалось найти пользователя"));
    }

    private double getBasicMetabolism(CustomerDto customerDto) {
        if (Sex.WOMEN == customerDto.getSex()) {
            return WEIGHT_COEFFICIENT * customerDto.getWeight()
                    + HEIGHT_COEFFICIENT * customerDto.getHeight()
                    - AGE_COEFFICIENT * customerDto.getAge()
                    - WOMEN_COEFFICIENT;
        } else {
            return WEIGHT_COEFFICIENT * customerDto.getWeight()
                    + HEIGHT_COEFFICIENT * customerDto.getHeight()
                    - AGE_COEFFICIENT * customerDto.getAge()
                    - MEN_COEFFICIENT;
        }
    }
    private CustomerDto setCaloriesForCustomerDto(CustomerDto customerDto) {
        customerDto.setBasicMetabolism(getBasicMetabolism(customerDto));
        customerDto.setWeightLossCalories(getWeightLossCalories(customerDto));
        customerDto.setWeightGainCalories(getWeightGainCalories(customerDto));
        customerDto.setWeightMaintainCalories(getWeightMaintainCalories(customerDto));
        return customerDto;
    }

    private double getWeightLossCalories(CustomerDto customerDto) {
        return getWeightMaintainCalories(customerDto) * WEIGHT_LOSS_COEFFICIENT;
    }

    private double getWeightGainCalories(CustomerDto customerDto) {
        return getWeightMaintainCalories(customerDto) * WEIGHT_GAIN_COEFFICIENT;
    }

    private double getWeightMaintainCalories(CustomerDto customerDto) {

        if (Activity.LOW == customerDto.getActivity()) {
            return getBasicMetabolism(customerDto) * LOW_ACTIVITY_COEFFICIENT;
        } else if (Activity.MEDIUM == customerDto.getActivity()) {
            return getBasicMetabolism(customerDto) * MEDIUM_ACTIVITY_COEFFICIENT;
        } else {
            return getBasicMetabolism(customerDto) * HIGH_ACTIVITY_COEFFICIENT;
        }
    }
}
