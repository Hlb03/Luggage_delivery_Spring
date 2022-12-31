package com.luggage_delivery.controller;
/*
  User: admin
  Cur_date: 28.12.2022
  Cur_time: 12:59
*/

import com.luggage_delivery.entity.Delivery;
import com.luggage_delivery.entity.DeliveryStatus;
import com.luggage_delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin-room")
public class ManagerRoomController {

    private final DeliveryService deliveryService;

    @Autowired
    public ManagerRoomController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    public String getManagerRoom(@RequestParam(value = "page",defaultValue = "1", required = false) int page,
                                 Principal principal, Model model) {

        Page<Delivery> deliveries = deliveryService.getAllDeliveries(page, DeliveryStatus.PROCESSING);
        System.out.println(deliveries.getTotalPages());

        model.addAttribute("userLogin", principal.getName());
        model.addAttribute("allOrders", deliveries);
        model.addAttribute("orderStatus", DeliveryStatus.PROCESSING);
        model.addAttribute("totalPages", deliveries.getTotalPages());
        model.addAttribute("page", page);

        return "manager-room";
    }

    @PostMapping("order-process/{orderId}")
    public String processOrder(@PathVariable int orderId,
                               @RequestParam (value = "processOrder")String typeOfProcess) {

        deliveryService.changeStatus(orderId, typeOfProcess);
        return "redirect:/admin-room";
    }
}
