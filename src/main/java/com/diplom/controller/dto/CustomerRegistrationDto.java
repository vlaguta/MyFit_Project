package com.diplom.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegistrationDto {

    private int id;

    @Size(min = 6, max = 15, message = "Количество знаков должно быть в диапазоне от 6 до 15")
    @NotEmpty(message = "Поле должно быть заполнено")
    private String login;

    @Size(min = 5, message = "Минимальное количество знаков - 5")
    @NotEmpty(message = "Поле должно быть заполнено")
    private String password;
}
