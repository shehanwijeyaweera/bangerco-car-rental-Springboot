package com.springbootweb.bangercocarrental.Repository;

import com.springbootweb.bangercocarrental.Model.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationModel, Long> {

    @Query("SELECT count(u) from ReservationModel u where u.vehical_no =?1 and ((u.startDate >= ?2 and u.endDate >= ?3 and u.startDate <= ?3) or (u.startDate >= ?2 and u.endDate <= ?3) or (u.startDate <= ?2 and u.endDate >= ?3) or (u.startDate <= ?2 and u.endDate >= ?2 and u.endDate <= ?3)) and u.active=FALSE")
    Long checkIfAvailable(Long car_id, LocalDateTime startDate, LocalDateTime endDate);

    @Query("select i from ReservationModel i where i.vehical_no=?1")
    List<ReservationModel> getAllReservationForCar(Long Car_id);

    @Query("select i from ReservationModel i where i.user.id=?1 order by i.reservation_id desc ")
    List<ReservationModel> getReservationForUser(Long user_id);

    @Query("select i from ReservationModel i where i.reservation_id=?1")
    ReservationModel getReservationDetails(Long reservation_id);

    @Query("SELECT count(r) from ReservationModel r where r.car.car_id = ?1 and ((r.startDate >= ?2 and r.endDate >= ?3 and r.startDate <= ?3) or (r.startDate >= ?2 and r.endDate <= ?3) or (r.startDate <= ?2 and r.endDate >= ?2 and r.endDate <= ?3)) and r.reservation_id <> ?4")
    Long updateIfAvailable(Long car_id, LocalDateTime startDate, LocalDateTime endDate, Long reservation_id);

    @Transactional
    @Modifying
    @Query("UPDATE ReservationModel i set i.active=TRUE where i.reservation_id=?1")
    void CancelReservation(Long reservation_id);

    @Transactional
    @Modifying
    @Query("UPDATE ReservationModel i set i.active=FALSE where i.reservation_id=?1")
    void ActiveReservation(Long reservation_id);

    @Query("select i from ReservationModel i order by i.reservation_id desc ")
    List<ReservationModel> getReservationAdminHomepage();

    @Query("select sum(i.fee) from ReservationModel i")
    Double getTotalRevenue();

    @Query("select count(u) from ReservationModel u where u.satNav = TRUE and ((u.startDate >= ?1 and u.endDate >= ?2 and u.startDate <= ?2) or (u.startDate >= ?2 and u.endDate <= ?2) or (u.startDate <= ?1 and u.endDate >= ?2) or (u.startDate <= ?2 and u.endDate >= ?1 and u.endDate <= ?2)) and u.active=FALSE ")
    Long checkIfAvailableSatNav(LocalDateTime startDate, LocalDateTime endDate);

    @Query("select count(u) from ReservationModel u where u.babyseat = TRUE and ((u.startDate >= ?1 and u.endDate >= ?2 and u.startDate <= ?2) or (u.startDate >= ?2 and u.endDate <= ?2) or (u.startDate <= ?1 and u.endDate >= ?2) or (u.startDate <= ?2 and u.endDate >= ?1 and u.endDate <= ?2)) and u.active=FALSE ")
    Long checkIfAvailableBabyseats(LocalDateTime startDate, LocalDateTime endDate);

    @Query("select count(u) from ReservationModel u where u.chiller = TRUE and ((u.startDate >= ?1 and u.endDate >= ?2 and u.startDate <= ?2) or (u.startDate >= ?2 and u.endDate <= ?2) or (u.startDate <= ?1 and u.endDate >= ?2) or (u.startDate <= ?2 and u.endDate >= ?1 and u.endDate <= ?2)) and u.active=FALSE ")
    Long checkIfAvailableChiller(LocalDateTime startDate, LocalDateTime endDate);
}
