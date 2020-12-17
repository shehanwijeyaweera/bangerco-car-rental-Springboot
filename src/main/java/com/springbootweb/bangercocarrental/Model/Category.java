package com.springbootweb.bangercocarrental.Model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long category_id;

    private String category_logo;
    private String category_name;
    private String category_discription;

    @ManyToMany(mappedBy = "category")
    private Set<CarModel> carModels;

    public Category() {
    }

    public Category(String category_logo, String category_name, String category_discription, Set<CarModel> carModels) {
        this.category_logo = category_logo;
        this.category_name = category_name;
        this.category_discription = category_discription;
        this.carModels = carModels;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public String getCategory_logo() {
        return category_logo;
    }

    public void setCategory_logo(String category_logo) {
        this.category_logo = category_logo;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_discription() {
        return category_discription;
    }

    public void setCategory_discription(String category_discription) {
        this.category_discription = category_discription;
    }

    public Set<CarModel> getCarModels() {
        return carModels;
    }

    public void setCarModels(Set<CarModel> carModels) {
        this.carModels = carModels;
    }

    @Transient
    public String getLogoImagePath(){
        if (category_logo == null || category_id == null) return null;

        return "/category_logos/" + category_id + "/" + category_logo;
    }
}
