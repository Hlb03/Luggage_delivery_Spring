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
    public String showOrderPage(@RequestParam(value = "totalPrice", required = false) BigDecimal totalPrice,
                                @RequestParam(value = "size", required = false) String size,
                                @RequestParam(value = "type", required = false) String type,
                                @RequestParam(value = "weight", required = false) String weight,
                                @RequestParam(value = "deliveryDate", required = false) String date,
                                @RequestParam(value = "address", required = false) String address,
                                @RequestParam(value = "routeId", required = false) String routeId,
                                @RequestParam(value = "option", required = false) String option,
                                Model model) {

        model.addAttribute("allRoutes", routeService.findRoutes());

        System.out.println("ATTRIBUTES AFTER PRICE CALC: " + totalPrice + " " + size + " " + type + " " + weight);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("size", size);
        model.addAttribute("type", type);
        model.addAttribute("weight", weight);
        model.addAttribute("deliveryDate", date);
        model.addAttribute("address", address);
        model.addAttribute("routeId", routeId);
        model.addAttribute("option", option);

        model.addAttribute("delivery", new Delivery());
        return "make-order";
    }

    @PostMapping("/order-process")
    public String orderProcess(@ModelAttribute("delivery")Delivery delivery,
                               @RequestParam(value = "option")String option,
                               Authentication authentication) {

        BigDecimal totalPrice = deliveryService.calculateOrderPrice(
                delivery.getLuggageSize(), delivery.getWeight(), delivery.getRoute().getId(), option);
        deliveryService.addNewDelivery(delivery.getLuggageSize(), delivery.getWeight(),
                                       delivery.getLuggageType(), delivery.getAddress(),
                                       delivery.getDeliveryDate(), delivery.getRoute().getId(),
                                       totalPrice, authentication.getName());
        return "redirect:/";
    }

    @PostMapping("/price-calculate")
    public String orderPriceCalculate(@ModelAttribute("delivery")Delivery delivery,
                                      @RequestParam(value = "option")String option){

        BigDecimal totalPrice = deliveryService.calculateOrderPrice(
                delivery.getLuggageSize(), delivery.getWeight(), delivery.getRoute().getId(), option);

        return "redirect:/make-order?totalPrice=" + totalPrice + "&size=" + delivery.getLuggageSize() +
                "&type=" + delivery.getLuggageType() + "&weight=" + delivery.getWeight()
                + "&deliveryDate=" + delivery.getDeliveryDate() + "&address=" + delivery.getAddress() +
                "&routeId=" + delivery.getRoute().getId() + "&option=" + option;
    }
}
