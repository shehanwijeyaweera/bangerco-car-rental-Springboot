package com.springbootweb.bangercocarrental.Repository;

import com.springbootweb.bangercocarrental.Model.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationModel, Long> {

    @Query("SELECT count(u) from ReservationModel u where u.vehical_no =?1 and ((u.startDate >= ?2 and u.endDate >= ?3 and u.startDate <= ?3) or (u.startDate >= ?2 and u.endDate <= ?3) or (u.startDate <= ?2 and u.endDate >= ?3) or (u.startDate <= ?2 and u.endDate >= ?2 and u.endDate <= ?3))")
    Long checkIfAvailable(Long car_id, LocalDateTime startDate, LocalDateTime endDate);

    @Query("select i from ReservationModel i where i.vehical_no=?1")
    List<ReservationModel> getAllReservationForCar(Long Car_id);
}
