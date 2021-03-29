package com.diplom.repository;

import com.diplom.model.DailyMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DailyMenuRepository extends JpaRepository<DailyMenu, Integer> {

    void deleteById(int id);

    Optional<DailyMenu> findByCustomerLoginAndCreatedDate(String login, LocalDate createdDate);

    Optional<DailyMenu> findByCustomerLogin(String login);

    Optional<DailyMenu> findByCustomerIdAndCreatedDate(int id, LocalDate createdDate);

    Optional<DailyMenu> findByCustomerId(int customerId/*, LocalDate createdDate*/);//надо раскомментить локал дейт, чтобы в контроллере не падала ошибка нотЮникРезалтЭксепшен и тогда можно в конроллере убрать вторую строчку модел адд атрибьют

}
