package com.luggage_delivery.controller;
/*
  User: admin
  Cur_date: 14.11.2022
  Cur_time: 14:38
*/

import com.luggage_delivery.entity.Delivery;
import com.luggage_delivery.service.DeliveryService;
import com.luggage_delivery.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
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
    public String showOrderPage(@RequestParam(value = "totalPrice", required = false) String totalPrice,
                                @RequestParam(value = "size", required = false) String size,
                                @RequestParam(value = "type", required = false) String type,
                                @RequestParam(value = "weight", required = false) String weight,
                                @RequestParam(value = "routeId", required = false) String routeId,
                                @RequestParam(value = "address", required = false) String address,
                                @RequestParam(value = "deliveryDate", required = false) Date deliveryDate,
                                @RequestParam(value = "option", required = false) String option,
                                Principal principal,
                                Model model) {

        model.addAttribute("allRoutes", routeService.findRoutes());

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("size", size);
        model.addAttribute("type", type);
        model.addAttribute("weight", weight);
        model.addAttribute("routeId", routeId);
        model.addAttribute("deliveryDate", deliveryDate);
        model.addAttribute("address", address);
        model.addAttribute("option", option);

        model.addAttribute("delivery", new Delivery());
        if (principal != null)
            model.addAttribute("userLogin", principal.getName());

        return "make-order";
    }

    @PostMapping("/order-process")
    public String orderProcess(@RequestParam("size") double size,
                               @RequestParam("type") String type,
                               @RequestParam("weight") double weight,
                               @RequestParam("deliveryDate") Date deliveryDate,
                               @RequestParam("address") String address,
                               @RequestParam("routeId") int routeId,
                               @RequestParam(value = "option") String option,
                               Authentication authentication) {

        BigDecimal totalPrice = deliveryService.calculateOrderPrice(
                size, weight, routeId, option);

        deliveryService.addNewDelivery(size, weight, type, address,
                deliveryDate, routeId,
                totalPrice, authentication.getName());
        return "redirect:/user-order";
    }

    @PostMapping("/price-calculate")
    public String orderPriceCalculate(@RequestParam(value = "size") double size,
                                      @RequestParam(value = "type") String type,
                                      @RequestParam(value = "weight") double weight,
                                      @RequestParam(value = "routeId") int routeId,
                                      @RequestParam(value = "deliveryDate") Date deliveryDate,
                                      @RequestParam(value = "address") String address,
                                      @RequestParam(value = "option") String option) {

        BigDecimal totalPrice = deliveryService.calculateOrderPrice(
                size, weight, routeId, option);

        return "redirect:/make-order?totalPrice=" + totalPrice + "&size=" + size +
                "&type=" + type + "&weight=" + weight
                + "&deliveryDate=" + deliveryDate + "&address=" + address +
                "&routeId=" + routeId + "&option=" + option;
    }
}
