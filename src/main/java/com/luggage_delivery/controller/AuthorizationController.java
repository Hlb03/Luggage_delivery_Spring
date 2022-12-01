package com.luggage_delivery.controller;
/*
  User: admin
  Cur_date: 18.11.2022
  Cur_time: 16:43
*/

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class AuthorizationController {

    @GetMapping
    public String singIn() {
        return "login";
    }
}
