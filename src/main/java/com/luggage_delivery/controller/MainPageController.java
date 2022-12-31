package com.luggage_delivery.controller;

import com.luggage_delivery.entity.Route;
import com.luggage_delivery.service.RouteService;
import com.luggage_delivery.service.TariffService;
import com.luggage_delivery.session_details.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class MainPageController {

    private final TariffService tariffService;
    private final RouteService routeService;
    private final UserInfo userInfo;

    @Autowired
    public MainPageController(TariffService tariffService, RouteService routeService, UserInfo userInfo) {
        this.tariffService = tariffService;
        this.routeService = routeService;
        this.userInfo = userInfo;
    }

    @GetMapping("")
    public String getAllTariffs(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "routeField", defaultValue = "distance", required = false) String routeField,
            @RequestParam(value = "typeOfRouteSort", defaultValue = "desc", required = false) String typeOfRouteSort,
            @RequestParam(value = "typeOfTariffSort", defaultValue = "desc", required = false) String typeOfTariffSort,
            @RequestParam(value = "tariffField", defaultValue = "price", required = false) String tariffField,
            Principal principal,
            Model model) {

        Page<Route> routes = routeService.findAllRoutes(page, routeField, typeOfRouteSort);
        model.addAttribute("allRoutes", routes);
        model.addAttribute("allTariffs", tariffService.getAllTariffs(tariffField, typeOfTariffSort));
        model.addAttribute("role", userInfo.getUserInfo());

        model.addAttribute("totalPages", routes.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("typeOfRouteSort", typeOfRouteSort);
        model.addAttribute("typeOfTariffSort", typeOfTariffSort);

        if (principal != null)
            model.addAttribute("userLogin", principal.getName());

        return "main-page";
    }
}
