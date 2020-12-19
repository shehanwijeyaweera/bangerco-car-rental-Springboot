package com.springbootweb.bangercocarrental.Controller;

import com.springbootweb.bangercocarrental.Model.CarModel;
import com.springbootweb.bangercocarrental.Model.Category;
import com.springbootweb.bangercocarrental.Repository.CarModelRepository;
import com.springbootweb.bangercocarrental.Repository.Car_CategoryRepository;
import com.springbootweb.bangercocarrental.Repository.UserRepository;
import com.springbootweb.bangercocarrental.Service.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Car_CategoryRepository car_categoryRepository;

    @Autowired
    private CarModelRepository carModelRepository;


    @GetMapping("/homepage")
    public String admin_homepage(){
        return "admin_homepage";
    }

    @GetMapping("/users")
    public String ViewAllUsers(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "admin_viewAllUsers";
    }

    @GetMapping("/cars/addnewcar")
    public String loadAddNewCarPage(){

        return "";
    }

    @GetMapping("/category/viewallcategories")
    public String viewAllCategories(Model model){
        model.addAttribute("categories", car_categoryRepository.findAll());
        return "admin_viewAllCategories";
    }

    @GetMapping("/category/addnewcategory")
    public String AddNewCategory(Model model){
        Category category = new Category();
        model.addAttribute("category", category);
        return "admin_addnewCategory";
    }

    @PostMapping("/category/save")
    public String saveNewCategory(@ModelAttribute("category") Category category, @RequestParam("logofile")MultipartFile logopic) throws IOException {
        String logoImage = StringUtils.cleanPath(logopic.getOriginalFilename());
        category.setCategory_logo(logoImage);

        Category savedCategory = car_categoryRepository.save(category);

        String uploadDir = "./category_logos/" + savedCategory.getCategory_id();

        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = logopic.getInputStream()) {
            Path filePath = uploadPath.resolve(logoImage);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new IOException("Could not save uploaded file: " + logoImage);
        }
        return "redirect:/admin/category/addnewcategory?success";
    }

    @GetMapping("/car/viewallcars")
    public String viewAllCars(Model model){
        model.addAttribute("cars", carModelRepository.findAll());
        return "admin_viewAllCars";
    }

    @GetMapping("/car/addnewcar")
    public String AddNewCar(Model model){
        CarModel carModel = new CarModel();
        List<Category> categories =  car_categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("cars", carModel);
        return "admin_addnewCar";
    }

    @PostMapping("/car/save")
    public String saveCar(@ModelAttribute("cars") CarModel carModel, @RequestParam("carImage")MultipartFile carpic) throws IOException {

        String carImage = StringUtils.cleanPath(carpic.getOriginalFilename());
        carModel.setCar_image(carImage);

        CarModel savedCar = carModelRepository.save(carModel);

        String uploadDir = "./car_images/" + savedCar.getCar_id();

        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = carpic.getInputStream()) {
            Path filePath = uploadPath.resolve(carImage);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new IOException("Could not save uploaded file: " + carImage);
        }

        return "redirect:/admin/car/addnewcar?success";
    }

    @GetMapping("/car/inactivate/{car_id}")
    public String inactivateCar(@PathVariable(value = "car_id")long car_id){
        Optional<CarModel> currentCar = carModelRepository.findById(car_id);
        currentCar.get().setEnabled(TRUE);
        carModelRepository.save(currentCar.get());
        return "redirect:/admin/car/viewallcars?inactivate";
    }

    @GetMapping("/car/activate/{car_id}")
    public String activateCar(@PathVariable(value = "car_id")long car_id){
        Optional<CarModel> currentCar = carModelRepository.findById(car_id);
        currentCar.get().setEnabled(FALSE);
        carModelRepository.save(currentCar.get());
        return "redirect:/admin/car/viewallcars?activate";
    }
}
