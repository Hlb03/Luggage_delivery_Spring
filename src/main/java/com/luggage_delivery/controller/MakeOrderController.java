package com.luggage_delivery.controller;
/*
  User: admin
  Cur_date: 14.11.2022
  Cur_time: 14:38
*/

import com.luggage_delivery.service.DeliveryService;
import com.luggage_delivery.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

@Controller
@RequestMapping("/make-order")
public class MakeOrderController {

    private final RouteService routeService;
    private final DeliveryService deliveryService;

    @Autowired
    public MakeOrderController(RouteService routeService, DeliveryService deliveryService) {
        this.routeService = routeService;
        this.deliveryService = deliveryService;
    }

    @GetMapping("")
    public String showOrderPage(Model model) {

        model.addAttribute("allRoutes", routeService.findRoutes());
        return "make-order";
    }

    @PostMapping("/order-process")
    public String orderProcess(@RequestParam(value = "size", required = true) double size,
                               @RequestParam(value = "weight", required = true) double weight,
                               @RequestParam(value = "type", required = true) String type,
                               @RequestParam(value = "routeId", required = true) int routeId,
                               @RequestParam(value = "deliveryDate", required = true)Date date,
                               @RequestParam(value = "address", required = true)String address,
                               @RequestParam(value = "option", required = true)String option) {

        deliveryService.addNewDelivery(size, weight, type, address, date, routeId, option);
        return "redirect:/";
    }
}
