package com.springbootweb.bangercocarrental.Model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carModel")
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long car_id;
    private String car_name;
    private String type;
    private String car_image;
    private String car_plate;
    private double rental_price;
    private String fuelType;
    private String description;
    private int yearofmade;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "carModel_category",
        joinColumns = { @JoinColumn(name = "car_id") },
        inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private Set<Category> category = new HashSet<>();

    private boolean enabled;

    public CarModel() {
    }

    public CarModel(String car_name, String type, String car_image, String car_plate, double rental_price, String fuelType, String description, int yearofmade, Set<Category> category, boolean enabled) {
        this.car_name = car_name;
        this.type = type;
        this.car_image = car_image;
        this.car_plate = car_plate;
        this.rental_price = rental_price;
        this.fuelType = fuelType;
        this.description = description;
        this.yearofmade = yearofmade;
        this.category = category;
        this.enabled = enabled;
    }

    public Long getCar_id() {
        return car_id;
    }

    public void setCar_id(Long car_id) {
        this.car_id = car_id;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCar_image() {
        return car_image;
    }

    public void setCar_image(String car_image) {
        this.car_image = car_image;
    }

    public String getCar_plate() {
        return car_plate;
    }

    public void setCar_plate(String car_plate) {
        this.car_plate = car_plate;
    }

    public double getRental_price() {
        return rental_price;
    }

    public void setRental_price(double rental_price) {
        this.rental_price = rental_price;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYearofmade() {
        return yearofmade;
    }

    public void setYearofmade(int yearofmade) {
        this.yearofmade = yearofmade;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Category> getCategory() {
        return category;
    }

    public void setCategory(Set<Category> category) {
        this.category = category;
    }
}
