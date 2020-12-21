package com.springbootweb.bangercocarrental.Controller;

import com.springbootweb.bangercocarrental.Model.User;
import com.springbootweb.bangercocarrental.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController{

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String home(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("Admin")))
        {
            return "redirect:/admin/homepage";
        }
        if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("User")))
        {
            String username;

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails)principal).getUsername();
            } else {
                username = principal.toString();
            }

            User user= userRepository.findByUsername(username);

            if(user.isEnabled() == true) {
                return "redirect:/blacklisted";
            }
            else {
                return "redirect:/user/homepage";
            }
        }
        return "redirect:/login";
    }
}
