package com.springbootweb.bangercocarrental.Service;

import com.springbootweb.bangercocarrental.Model.User;
import com.springbootweb.bangercocarrental.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
        User save(UserRegistrationDto registrationDto);
}
