package com.springbootweb.bangercocarrental.Controller;

import com.springbootweb.bangercocarrental.Model.User;
import com.springbootweb.bangercocarrental.Repository.DmvModelRepository;
import com.springbootweb.bangercocarrental.Service.UserService;
import com.springbootweb.bangercocarrental.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Boolean.TRUE;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private DmvModelRepository dmvModelRepository;

    @Autowired
    private JavaMailSender mailSender;

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
    public String registerUserAccount(@ModelAttribute("user")UserRegistrationDto registrationDto, @RequestParam("driverslicence")MultipartFile driverslicencepic, @RequestParam("taxstatement")MultipartFile taxstatementpic) throws IOException, MessagingException {

        //check if provided NIC in DMV database
        Long userExist = dmvModelRepository.checkIfExist(registrationDto.getNIC());
        if(userExist==1){
            registrationDto.setEnabled(TRUE);
            sendDMVEmail(registrationDto);
        }

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

    private void sendDMVEmail(UserRegistrationDto userRegistrationDto) throws MessagingException, UnsupportedEncodingException {
        String subject = "lost or stolen NIC registered in our System";
        String senderName = "Banger & Co";
        String mailContent = "<h2>User details</h2><br>";
               mailContent += "<p>First Name : "+userRegistrationDto.getUser_fName()+"</p><br>";
        mailContent += "<p>Last Name : "+userRegistrationDto.getUser_lName()+"</p><br>";
        mailContent += "<p>Address : "+userRegistrationDto.getUser_address()+"</p><br>";
        mailContent += "<p>NIC : "+userRegistrationDto.getNIC()+"</p><br>";

        MimeMessage message =mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("onlinebookstorelk@gmail.com", senderName);
        helper.setTo("ponikax637@nonicamy.com");
        helper.setSubject(subject);

        helper.setText(mailContent, true);

        mailSender.send(message);
    }
}
