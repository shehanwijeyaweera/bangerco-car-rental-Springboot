package com.springbootweb.bangercocarrental.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    @GetMapping("/homepage")
    public String admin_homepage(){
        return "admin_homepage";
    }
}
