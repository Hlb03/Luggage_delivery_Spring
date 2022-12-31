package com.luggage_delivery.controller;
/*
  User: admin
  Cur_date: 18.11.2022
  Cur_time: 16:43
*/

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class AuthorizationController {

    @GetMapping
    public String singIn(@RequestParam(value = "error", required = false) String error,
                         Model model) {
        if (error != null)
            model.addAttribute("error", "Incorrect login/password");
        return "login";
    }
}
