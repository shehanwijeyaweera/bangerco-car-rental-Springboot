package com.springbootweb.bangercocarrental.Repository;

import com.springbootweb.bangercocarrental.Model.DmvModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DmvModelRepository extends JpaRepository<DmvModel, Long> {

    @Query("select count(u) from DmvModel u where u.NIC=?1")
    Long checkIfExist(String nic);
}
