package com.springbootweb.bangercocarrental.Repository;

import com.springbootweb.bangercocarrental.Model.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationModel, Long> {

}
