package com.diplom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Data
@Table(name = "daily_menu")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "created_date")
    private LocalDate createdDate;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_daily_menu",
            joinColumns = @JoinColumn(name = "daily_menu_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @OneToOne(optional = false, mappedBy = "dailyMenu")
    private Customer customer;
    @Column(name = "general_calories")
    private int generalCalories;
    @Column(name = "general_proteins")
    private int generalProteins;
    @Column(name = "general_fats")
    private int generalFats;
    @Column(name = "general_carbonhydrates")
    private int generalCarbonhydrates;

}
