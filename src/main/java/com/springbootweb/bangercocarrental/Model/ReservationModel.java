package com.springbootweb.bangercocarrental.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class ReservationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long reservation_id;

    private Long vehical_no;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private CarModel car;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private Date created_at;
    private double fee;
    private boolean active;
    private boolean lateReturn;
    private boolean lateReturnReq;
    private boolean satNav;
    private boolean babyseat;
    private boolean chiller;
    private String pickedUp;

    public ReservationModel() {
    }

    public ReservationModel(Long vehical_no, User user, CarModel car, LocalDateTime startDate, LocalDateTime endDate, Date created_at, double fee, boolean active, boolean lateReturn, boolean lateReturnReq, boolean satNav, boolean babyseat, boolean chiller, String pickedUp) {
        this.vehical_no = vehical_no;
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.created_at = created_at;
        this.fee = fee;
        this.active = active;
        this.lateReturn = lateReturn;
        this.lateReturnReq = lateReturnReq;
        this.satNav = satNav;
        this.babyseat = babyseat;
        this.chiller = chiller;
        this.pickedUp = pickedUp;
    }

    public boolean isSatNav() {
        return satNav;
    }

    public void setSatNav(boolean satNav) {
        this.satNav = satNav;
    }

    public boolean isBabyseat() {
        return babyseat;
    }

    public void setBabyseat(boolean babyseat) {
        this.babyseat = babyseat;
    }

    public boolean isChiller() {
        return chiller;
    }

    public void setChiller(boolean chiller) {
        this.chiller = chiller;
    }

    public boolean isLateReturnReq() {
        return lateReturnReq;
    }

    public void setLateReturnReq(boolean lateReturnReq) {
        this.lateReturnReq = lateReturnReq;
    }

    public boolean isLateReturn() {
        return lateReturn;
    }

    public void setLateReturn(boolean lateReturn) {
        this.lateReturn = lateReturn;
    }

    public Long getVehical_no() {
        return vehical_no;
    }

    public void setVehical_no(Long vehical_no) {
        this.vehical_no = vehical_no;
    }

    public Long getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Long reservation_id) {
        this.reservation_id = reservation_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CarModel getCar() {
        return car;
    }

    public void setCar(CarModel car) {
        this.car = car;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(String pickedUp) {
        this.pickedUp = pickedUp;
    }
}
