package com.springbootweb.bangercocarrental.Controller;

import com.springbootweb.bangercocarrental.Model.User;
import com.springbootweb.bangercocarrental.Service.UserService;
import com.springbootweb.bangercocarrental.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto(){
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user")UserRegistrationDto registrationDto, @RequestParam("driverslicence")MultipartFile driverslicencepic, @RequestParam("taxstatement")MultipartFile taxstatementpic) throws IOException {

        String drivinglicenceImage = StringUtils.cleanPath(driverslicencepic.getOriginalFilename());
        registrationDto.setDocument1(drivinglicenceImage);

        String taxstatementImage = StringUtils.cleanPath(taxstatementpic.getOriginalFilename());
        registrationDto.setDocument2(taxstatementImage);

        User savedUser = userService.save(registrationDto);

        String uploadDir = "./documents/" + savedUser.getId();

        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = driverslicencepic.getInputStream()) {
            Path filePath = uploadPath.resolve(drivinglicenceImage);
            Path filePath1 = uploadPath.resolve(taxstatementImage);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(inputStream, filePath1, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new IOException("Could not save uploaded file: " + taxstatementImage + " & " + taxstatementImage);
        }

        return "redirect:/registration?success";
    }
}
