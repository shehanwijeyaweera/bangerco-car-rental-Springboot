package com.springbootweb.bangercocarrental.Service;

import com.springbootweb.bangercocarrental.Model.User;
import com.springbootweb.bangercocarrental.web.dto.UserRegistrationDto;

public interface UserService {
        User save(UserRegistrationDto registrationDto);
}
