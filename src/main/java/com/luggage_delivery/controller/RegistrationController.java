package com.luggage_delivery.controller;
/*
  User: admin
  Cur_date: 18.11.2022
  Cur_time: 17:45
*/

import com.luggage_delivery.entity.Status;
import com.luggage_delivery.entity.User;
import com.luggage_delivery.exceptions.UserRegistrateException;
import com.luggage_delivery.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final static Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registrationForm(@RequestParam(value = "error", required = false) String error,
                                   Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("error", error);
        return "registration";
    }

    @PostMapping("/addNewUser")
    public String createNewUser(@ModelAttribute("user") User user,
                                @RequestParam("password2") String secondPassword) throws UserRegistrateException {

        userService.addUser(user, secondPassword);
        LOG.debug("USER WITH LOGIN " + user.getLogin() + " WAS SUCCESSFULLY ADDED");
        return "redirect:/login";
    }

    @GetMapping("/submit/{activationCode}")
    public String activateUser(@PathVariable String activationCode) {
        User user = userService.activateUserByCode(activationCode);
        if (user == null)
            return "redirect:/registration";

        user.setStatusName(Status.ACTIVE);
        user.setActivationCode(null);
        userService.updateUserStatus(user);

        LOG.debug("USER WITH LOGIN " + user.getLogin() + " WAS SUCCESSFULLY ACTIVATED");
        return "redirect:/login";
    }
}
