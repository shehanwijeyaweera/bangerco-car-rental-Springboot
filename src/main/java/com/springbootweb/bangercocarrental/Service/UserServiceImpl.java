package com.springbootweb.bangercocarrental.Service;

import com.springbootweb.bangercocarrental.Model.Role;
import com.springbootweb.bangercocarrental.Model.User;
import com.springbootweb.bangercocarrental.Repository.UserRepository;
import com.springbootweb.bangercocarrental.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getUsername(), registrationDto.getPassword(), registrationDto.getUser_fName(),
                registrationDto.getUser_lName(), registrationDto.getUser_email(), registrationDto.getBirthday(),
                registrationDto.getUser_phoneNo(), registrationDto.getUser_address(), registrationDto.getDocuments(),
                registrationDto.isEnabled(), Arrays.asList(new Role("User")));

        return userRepository.save(user);
    }
}
