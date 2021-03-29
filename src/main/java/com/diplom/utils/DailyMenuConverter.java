package com.diplom.utils;

import com.diplom.controller.dto.DailyMenuDto;
import com.diplom.model.DailyMenu;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DailyMenuConverter {

    public static DailyMenuDto convertDailyMenuEntityToDailyMenuDto(DailyMenu dailyMenu) {

        return DailyMenuDto.builder()
                .name(dailyMenu.getName())
                .generalCalories(dailyMenu.getGeneralCalories())
                .generalProteins(dailyMenu.getGeneralProteins())
                .generalFats(dailyMenu.getGeneralFats())
                .generalCarbonhydrates(dailyMenu.getGeneralCarbonhydrates())
                .createdDate(dailyMenu.getCreatedDate())
                .build();
    }

    public static DailyMenu convertDailyMenuDtoToDailyMenuEntity(DailyMenuDto dailyMenuDto) {

        return DailyMenu.builder()
                .name(dailyMenuDto.getName())
                .generalCalories(dailyMenuDto.getGeneralCalories())
                .generalProteins(dailyMenuDto.getGeneralProteins())
                .generalFats(dailyMenuDto.getGeneralFats())
                .generalCarbonhydrates(dailyMenuDto.getGeneralCarbonhydrates())
                .createdDate(dailyMenuDto.getCreatedDate())
                .build();
    }
}
