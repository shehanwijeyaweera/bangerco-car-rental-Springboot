package com.springbootweb.bangercocarrental.Controller;

import com.springbootweb.bangercocarrental.Model.CarModel;
import com.springbootweb.bangercocarrental.Model.ReservationModel;
import com.springbootweb.bangercocarrental.Model.User;
import com.springbootweb.bangercocarrental.Repository.CarModelRepository;
import com.springbootweb.bangercocarrental.Repository.ReservationRepository;
import com.springbootweb.bangercocarrental.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarModelRepository carModelRepository;

    @Autowired
    private ReservationRepository reservationRepository;

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
        //age restricting car
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
        model.addAttribute("oldbookings", reservationRepository.getAllReservationForCar(id));
        return "user_viewCarDetails";
    }

    @GetMapping("/car/showbookingpage/{car_id}")
    public String showCarBookingPage(@PathVariable("car_id")Long id, Model model){
        Optional<CarModel> carModel = carModelRepository.findById(id);
        ReservationModel reservationModel = new ReservationModel();
        model.addAttribute("car_details", carModel);
        model.addAttribute("reservation", reservationModel);
        model.addAttribute("oldbookings", reservationRepository.getAllReservationForCar(id));
        return "user_bookCar";
    }

    @PostMapping("car/booking/save/{car_id}")
    public String saveBooking(@ModelAttribute("reservation")ReservationModel reservationModel,@PathVariable("car_id")Long id){
        //1. validate given time period (this part is done in the front end)
        String errorMessage = null;

        //2. check if the dates are available for the given time period.
        Long reservations = reservationRepository.checkIfAvailable(id, reservationModel.getStartDate(), reservationModel.getEndDate());
        if(reservations > 0){
            //error message
            return "redirect:/user/car/showbookingpage/"+id+"?error";
        }

        //check if satNav available
        if(reservationModel.isSatNav()==TRUE) {
            reservations = reservationRepository.checkIfAvailableSatNav(reservationModel.getStartDate(), reservationModel.getEndDate());
            if (reservations > 0) {
                //error message
                errorMessage = "?satNav";
            }
        }

        //check if babySeat available
        if(reservationModel.isBabyseat()==TRUE) {
            reservations = reservationRepository.checkIfAvailableBabyseats(reservationModel.getStartDate(), reservationModel.getEndDate());
            if (reservations > 0) {
                //error message
                errorMessage = "?babyseat";
            }
        }

        //check if chiller available
        if(reservationModel.isChiller()==TRUE) {
            reservations = reservationRepository.checkIfAvailableChiller(reservationModel.getStartDate(), reservationModel.getEndDate());
            if (reservations > 0) {
                //error message
                errorMessage = "?chiller";
            }
        }

       if(errorMessage!=null){
           return "redirect:/user/car/showbookingpage/"+id+errorMessage;
       }

        //set reservation details
        reservationModel.getStartDate();
        reservationModel.getEndDate();
        reservationModel.setVehical_no(id);
        reservationModel.getFee();
        reservationModel.setCreated_at(new Date());
        reservationModel.setActive(FALSE);



        //get user details

        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = userRepository.findByUsername(username);

        //set user details

        reservationModel.setUser(user);

        //get car details

        CarModel carModel = carModelRepository.getCarDetails(reservationModel.getVehical_no());

        reservationModel.setCar(carModel);

        //save reservation details

        reservationRepository.save(reservationModel);

        return "redirect:/user/homepage?success";
    }

    @GetMapping("/bookings/showall")
    public String showUserAllBookings(Model model){
        //get user details from session

        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = userRepository.findByUsername(username);


        model.addAttribute("userReservations" , reservationRepository.getReservationForUser(user.getId()));

        return "user_showAllBookings";
    }

    @GetMapping("/booking/edit/{reservation_id}")
    public String showeditReservationDetails(@PathVariable(value = "reservation_id")Long res_id,Model model){
        //get reservation current details
        model.addAttribute("res_details", reservationRepository.getReservationDetails(res_id));
        //car past bookings
        ReservationModel reservationModel = reservationRepository.getReservationDetails(res_id);
        model.addAttribute("oldbookings", reservationRepository.getAllReservationForCar(reservationModel.getVehical_no()));

        Optional<CarModel> carModel = carModelRepository.findById(reservationModel.getVehical_no());
        model.addAttribute("car_details", carModel);
        model.addAttribute("reservation", reservationModel);
        return "user_editReservation";
    }

    @PostMapping("/booking/edit/save")
    public String saveEditedBooking(@ModelAttribute("reservation")ReservationModel reservationModel){
        //1. validate given time period (this part is done in the front end)

        //2. check if the dates are available for the given time period.
        Long reservations = reservationRepository.updateIfAvailable(reservationModel.getVehical_no(), reservationModel.getStartDate(), reservationModel.getEndDate(), reservationModel.getReservation_id());
        if(reservations > 0){
            //error message
            return "redirect:/user/booking/edit/"+reservationModel.getReservation_id()+"?error";
        }

        //set reservation details
        reservationModel.getStartDate();
        reservationModel.getEndDate();
        reservationModel.setVehical_no(reservationModel.getVehical_no());
        reservationModel.getFee();
        reservationModel.setCreated_at(new Date());
        reservationModel.setActive(FALSE);
        reservationModel.setLateReturn(FALSE);
        reservationModel.setLateReturnReq(FALSE);

        //get user details

        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = userRepository.findByUsername(username);

        //set user details

        reservationModel.setUser(user);

        //get car details

        CarModel carModel = carModelRepository.getCarDetails(reservationModel.getVehical_no());

        reservationModel.setCar(carModel);

        //save reservation details

        reservationRepository.save(reservationModel);

        return "redirect:/user/bookings/showall?success";
    }

    @GetMapping("/editUserdetails")
    public String showUserDetails(Model model){
        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        model.addAttribute("user_details", userRepository.findByUsername(username));

        return "user_viewUserDetails";
    }

    @PostMapping("/editUserdetails/save")
    public String saveEditedUserDetails(@ModelAttribute("user_details")User user){
        //get current user details
        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        User Currentuser = userRepository.findByUsername(username);

        Currentuser.setUser_fName(user.getUser_fName());
        Currentuser.setUser_lName(user.getUser_lName());
        Currentuser.setUser_email(user.getUser_email());
        Currentuser.setUser_phoneNo(user.getUser_phoneNo());
        Currentuser.setUser_address(user.getUser_address());
        //save user details
        userRepository.save(Currentuser);

        return "redirect:/user/editUserdetails?success";
    }

    @GetMapping("/cancelRes/{res_id}")
    public String cancelReservation(@PathVariable(value = "res_id")Long id){
        reservationRepository.CancelReservation(id);
        return "redirect:/user/bookings/showall?cancel";
    }

    @GetMapping("/requestLateReturn/{reservation_id}")
    public String requestLateReturn(@PathVariable(value = "reservation_id")Long reservationID){

        //get current user details
        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        User Currentuser = userRepository.findByUsername(username);

        //check if client has previously rented vehicles
        Long reservations = reservationRepository.getPreviousReservationCount(Currentuser.getId());
        if(reservations == 1){
            return "redirect:/user/bookings/showall?latereturnError";
        }

        //get the reservation
        Optional<ReservationModel> reservationModel = reservationRepository.findById(reservationID);

        //update value
        reservationModel.get().setLateReturnReq(TRUE);

        //save
        reservationRepository.save(reservationModel.get());

        return "redirect:/user/bookings/showall?latereturnSuccess";
    }

}
