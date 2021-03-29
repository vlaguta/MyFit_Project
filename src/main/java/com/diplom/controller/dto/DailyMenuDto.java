package com.diplom.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyMenuDto {

    private int id;
    private String name;
    private List<ProductDto> breakfast;
    private List<ProductDto> dinner;
    private List<ProductDto> supper;
    private int generalCalories;
    private int generalProteins;
    private int generalFats = 0;
    private int generalCarbonhydrates = 0;
    private LocalDate createdDate;

}
