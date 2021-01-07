package com.springbootweb.bangercocarrental.Controller;

import com.springbootweb.bangercocarrental.Model.CarModel;
import com.springbootweb.bangercocarrental.Repository.CarModelRepository;
import com.springbootweb.bangercocarrental.Repository.Car_CategoryRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@EnableScheduling
public class WebScraperController {

    @Autowired
    private CarModelRepository carModelRepository;

    @Scheduled(initialDelay = 3000L, fixedDelay = 10000L)
    public void scraperTask() throws IOException {
        Document doc = Jsoup.connect("https://www.amerirentacar.com/self-drive-rates-in-sri-lanka/").timeout(6000).get();

        Elements body = doc.select("tbody.row-hover");

        for (Element e : body.select("tr")) {
            String CarTitle = e.select("td.column-1 h2").text();
            if (CarTitle.matches("")) {
                String carName = e.select("td.column-1 span").text();
                String carPerWeek = e.select("td.column-3 span").text();
                if (!carName.matches("CONTINENTAL CARS")) {
                    //check in database
                    int carExist = 0;
                    carExist = carModelRepository.checkIfNameExist(carName);
                    if (carExist == 1) {
                        //get car details
                        CarModel carModel = carModelRepository.findByCarName(carName);
                        System.out.println("Car Name: " + carName);
                        carPerWeek = carPerWeek.replaceAll("\\s+", "");
                        Double priceWeek = Double.parseDouble(carPerWeek.substring(carPerWeek.lastIndexOf("$") + 1));
                        //converting USD to pounds per day
                        Double priceDayPounds = priceWeek * 0.1057142857142857;
                        //updating car rent
                        carModel.setRental_price(round(priceDayPounds, 2));
                        //save car Model
                        carModelRepository.save(carModel);
                        System.out.println("Car Name : "+carName+" ,Changed Rent For Per Day(F): " + round(priceDayPounds, 2));
                        System.out.println("_________________________________");

                    }
                }
            }
        }
    }

    //decimal round function
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
