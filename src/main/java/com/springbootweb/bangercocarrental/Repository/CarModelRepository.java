package com.springbootweb.bangercocarrental.Repository;

import com.springbootweb.bangercocarrental.Model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    @Query("UPDATE CarModel i set i.enabled=FALSE where i.car_id=?1")
    void activateCar(Long car_id);

    @Query("UPDATE CarModel i set i.enabled=TRUE where i.car_id=?1")
    void inactivateCar(Long car_id);
}
