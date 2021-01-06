package com.springbootweb.bangercocarrental.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DmvModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE )
    private Long ID;
    private String NIC;
    private String FName;
    private  String LName;

    public DmvModel(String NIC, String FName, String LName) {
        this.NIC = NIC;
        this.FName = FName;
        this.LName = LName;
    }

    public DmvModel() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }
}
