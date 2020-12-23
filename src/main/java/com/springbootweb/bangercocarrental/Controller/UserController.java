package com.springbootweb.bangercocarrental.Controller;

import com.springbootweb.bangercocarrental.Model.CarModel;
import com.springbootweb.bangercocarrental.Model.ReservationModel;
import com.springbootweb.bangercocarrental.Model.User;
import com.springbootweb.bangercocarrental.Repository.CarModelRepository;
import com.springbootweb.bangercocarrental.Repository.ReservationRepository;
import com.springbootweb.bangercocarrental.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

import static java.lang.Boolean.FALSE;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarModelRepository carModelRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/homepage")
    public String user_homepage(Model model){
        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user= userRepository.findByUsername(username);

        if(user.getAge()>=25){
            model.addAttribute("cars", carModelRepository.getAllActiveCars());
        }else {
            model.addAttribute("cars", carModelRepository.getSmallTownCars());
        }
        return "user_homepage";
    }

    @GetMapping("/car/details/{car_id}")
    public String viewCardDetails(@PathVariable("car_id")Long id, Model model){
        Optional<CarModel> carModel = carModelRepository.findById(id);
        model.addAttribute("car_details", carModel);
        return "user_viewCarDetails";
    }

    @GetMapping("/car/showbookingpage/{car_id}")
    public String showCarBookingPage(@PathVariable("car_id")Long id, Model model){
        Optional<CarModel> carModel = carModelRepository.findById(id);
        ReservationModel reservationModel = new ReservationModel();
        model.addAttribute("car_details", carModel);
        model.addAttribute("reservation", reservationModel);
        return "user_bookCar";
    }

    @PostMapping("car/booking/save/{car_id}")
    public String saveBooking(@ModelAttribute("reservation")ReservationModel reservationModel,@PathVariable("car_id")Long id){
        reservationModel.getStartDate();
        reservationModel.getEndDate();
        reservationModel.setVehical_no(id);
        reservationModel.getFee();
        reservationModel.setCreated_at(new Date());
        reservationModel.setActive(FALSE);

        //get user details

        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = userRepository.findByUsername(username);

        reservationModel.setUser(user);

        //get car details

        CarModel carModel = carModelRepository.getCarDetails(reservationModel.getVehical_no());

        reservationModel.setCar(carModel);

        //save reservation details

        reservationRepository.save(reservationModel);

        return "redirect:/user/homepage?success";
    }
}
