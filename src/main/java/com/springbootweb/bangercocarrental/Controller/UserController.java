package com.springbootweb.bangercocarrental.Controller;

import com.springbootweb.bangercocarrental.Model.CarModel;
import com.springbootweb.bangercocarrental.Model.User;
import com.springbootweb.bangercocarrental.Repository.CarModelRepository;
import com.springbootweb.bangercocarrental.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarModelRepository carModelRepository;

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
}
