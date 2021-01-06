package com.springbootweb.bangercocarrental.Model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String username;
    private String password;
    private String user_fName;
    private String user_lName;
    private String user_email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private int user_phoneNo;
    private String user_address;
    private String document1;
    private String document2;
    private boolean enabled;
    private String NIC;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Role> userRole;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<ReservationModel> reservations = new ArrayList<>();

    public User(String username, String password, String user_fName, String user_lName, String user_email, Date birthday, int user_phoneNo, String user_address, String document1, String document2, boolean enabled, String NIC, Collection<Role> userRole) {
        this.username = username;
        this.password = password;
        this.user_fName = user_fName;
        this.user_lName = user_lName;
        this.user_email = user_email;
        this.birthday = birthday;
        this.user_phoneNo = user_phoneNo;
        this.user_address = user_address;
        this.document1 = document1;
        this.document2 = document2;
        this.enabled = enabled;
        this.NIC = NIC;
        this.userRole = userRole;
    }

    public User() {
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_fName() {
        return user_fName;
    }

    public void setUser_fName(String user_fName) {
        this.user_fName = user_fName;
    }

    public String getUser_lName() {
        return user_lName;
    }

    public void setUser_lName(String user_lName) {
        this.user_lName = user_lName;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getUser_phoneNo() {
        return user_phoneNo;
    }

    public void setUser_phoneNo(int user_phoneNo) {
        this.user_phoneNo = user_phoneNo;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Collection<Role> getUserRole() {
        return userRole;
    }

    public void setUserRole(Collection<Role> userRole) {
        this.userRole = userRole;
    }

    public String getDocument1() {
        return document1;
    }

    public void setDocument1(String document1) {
        this.document1 = document1;
    }

    public String getDocument2() {
        return document2;
    }

    public void setDocument2(String document2) {
        this.document2 = document2;
    }

    public List<ReservationModel> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationModel> reservations) {
        this.reservations = reservations;
    }

    @Transient
    public String getDrivingLicenceImagePath(){
        if (document1 == null || id == null) return null;

        return "/documents/" + id + "/" + document1;
    }

    @Transient
    public String getTaxStatementImagePath(){
        if (document2 == null || id == null) return null;

        return "/documents/" + id + "/" + document2;
    }

    @Transient
    public int getAge(){
        LocalDate birthdate = convertToLocalDateViaInstant(birthday);
        if ((birthdate != null)) {
            return Period.between(birthdate, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
