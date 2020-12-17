package com.springbootweb.bangercocarrental.Repository;


import com.springbootweb.bangercocarrental.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Car_CategoryRepository extends JpaRepository<Category, Long> {

}
