package com.springbootweb.bangercocarrental.Controller;

import ch.qos.logback.core.util.FixedDelay;
import com.springbootweb.bangercocarrental.Model.DmvModel;
import com.springbootweb.bangercocarrental.Repository.DmvModelRepository;
import com.springbootweb.bangercocarrental.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

@Controller
@EnableScheduling
public class ScheduledController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DmvModelRepository dmvModelRepository;


    @Scheduled(cron = "0 2 12 * * ?")
    public void UpdateDMVDoc(){
        String line = "";
        String newNIC = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/DMVDocs/stolenlist.csv"));
            while((line=br.readLine())!=null){
                String [] data = line.split(",");
                DmvModel dmvModel = new DmvModel();
                dmvModel.setNIC(data[0]);
                dmvModel.setFName(data[1]);
                dmvModel.setLName(data[2]);
                //check before save
                Long userExist = dmvModelRepository.checkIfExist(dmvModel.getNIC());
                if(userExist==0) {
                    dmvModelRepository.save(dmvModel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void someJob() throws InterruptedException {
        System.out.println("Now is " + new Date());
        Thread.sleep(1000L);
    }
}
