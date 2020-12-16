package com.springbootweb.bangercocarrental.Controller;

import com.springbootweb.bangercocarrental.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/homepage")
    public String admin_homepage(){
        return "admin_homepage";
    }

    @GetMapping("/users")
    public String ViewAllUsers(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "admin_viewAllUsers";
    }
}
