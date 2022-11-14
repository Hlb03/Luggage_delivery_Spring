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

import java.math.BigDecimal;
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
    public String showOrderPage(@RequestParam(value = "totalPrice", required = false) BigDecimal totalPrice,
                                @RequestParam(value = "size", required = false) String size,
                                @RequestParam(value = "type", required = false) String type,
                                @RequestParam(value = "weight", required = false) String weight,
                                @RequestParam(value = "deliveryDate", required = false) String date,
                                @RequestParam(value = "address", required = false) String address,
                                @RequestParam(value = "routeId", required = false) String routeId,
                                @RequestParam(value = "option", required = false) String option,
                                Model model) {
        System.out.println("TOTAL PRICE IS: " + totalPrice + " SIZE = " + size);

        model.addAttribute("allRoutes", routeService.findRoutes());

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("size", size);
        model.addAttribute("type", type);
        model.addAttribute("weight", weight);
        model.addAttribute("deliveryDate", date);
        model.addAttribute("address", address);
        model.addAttribute("routeId", routeId);
        model.addAttribute("option", option);
        return "make-order";
    }

    @PostMapping("/order-process")
    public String orderProcess(@RequestParam(value = "size") double size,
                               @RequestParam(value = "weight") double weight,
                               @RequestParam(value = "type") String type,
                               @RequestParam(value = "routeId") int routeId,
                               @RequestParam(value = "deliveryDate")Date date,
                               @RequestParam(value = "address")String address,
                               @RequestParam(value = "option")String option) {

        deliveryService.addNewDelivery(size, weight, type, address, date, routeId, option);
        return "redirect:/";
    }

    @PostMapping("/price-calculate")
    public String orderPriceCalculate(@RequestParam(value = "size") double size,
                                      @RequestParam(value = "weight") double weight,
                                      @RequestParam(value = "type") String type,
                                      @RequestParam(value = "routeId") int routeId,
                                      @RequestParam(value = "deliveryDate")Date date,
                                      @RequestParam(value = "address")String address,
                                      @RequestParam(value = "option")String option) {

        BigDecimal totalPrice = deliveryService.calculateOrderPrice(size, weight, routeId, option);
        return "redirect:/make-order?totalPrice=" + totalPrice + "&size=" + size + "&type=" + type +
                "&weight=" + weight + "&deliveryDate=" + date + "&address=" + address +
                "&routeId=" + routeId + "&option=" + option;
    }
}
