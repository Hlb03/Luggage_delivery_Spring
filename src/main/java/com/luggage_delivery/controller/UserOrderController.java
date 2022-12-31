package com.luggage_delivery.controller;
/*
  User: admin
  Cur_date: 26.12.2022
  Cur_time: 10:06
*/

import com.luggage_delivery.entity.Delivery;
import com.luggage_delivery.entity.User;
import com.luggage_delivery.exceptions.LackOfBalanceException;
import com.luggage_delivery.service.DeliveryService;
import com.luggage_delivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@RequestMapping("/user-order")
public class UserOrderController {

    private final DeliveryService deliveryService;
    private final UserService userService;

    @Autowired
    public UserOrderController(DeliveryService deliveryService, UserService userService) {
        this.deliveryService = deliveryService;
        this.userService = userService;
    }

    @GetMapping
    public String viewOrders(@RequestParam(value = "page", defaultValue = "1", required = false)int page,
                             @RequestParam(value = "error", required = false) String errorInfo,
                             Principal principal, Model model) {

        User user = userService.getUserByLogin(principal.getName());
        Page<Delivery> userDeliveries = deliveryService.getUserDeliveries(user, page);

        model.addAttribute("userOrders", userDeliveries);
        model.addAttribute("userBalance", user.getBalance());
        model.addAttribute("page", page);
        model.addAttribute("totalPages", userDeliveries.getTotalPages());
        model.addAttribute("error", errorInfo);
        return "user-order";
    }

    @PostMapping("/add-balance")
    public String balanceReplenishment(
            @RequestParam(value = "balanceIncrement", required = false)BigDecimal balanceIncrement,
            Principal principal) {
        userService.updateUserBalance(balanceIncrement, principal.getName());
        return "redirect:/user-order";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrder(@PathVariable int orderId,
                           Principal principal) throws LackOfBalanceException {
            deliveryService.payOrder(orderId, principal.getName());

        return "redirect:/user-order";
    }
}