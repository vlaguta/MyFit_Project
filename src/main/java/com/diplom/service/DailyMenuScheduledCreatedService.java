package com.diplom.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DailyMenuScheduledCreatedService {

    private final DailyMenuService dailyMenuService;

    public DailyMenuScheduledCreatedService(DailyMenuService dailyMenuService) {
        this.dailyMenuService = dailyMenuService;
    }

    /**
     * Method save Daily Menu for every customer every day at 12 am
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void createDailyMenuForAllCustomersTask() {
        dailyMenuService.saveDailyMenuForEveryCustomer();
    }
}
