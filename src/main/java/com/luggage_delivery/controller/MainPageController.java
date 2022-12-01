package com.luggage_delivery.entity.controller;

import com.luggage_delivery.service.RouteService;
import com.luggage_delivery.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.luggage_delivery.util.PageMaxCountUtil.countTotalAmountOfPage;

@Controller
public class MainPageController {

    private final TariffService tariffService;
    private final RouteService routeService;

    @Autowired
    public MainPageController(TariffService tariffService, RouteService routeService) {
        this.tariffService = tariffService;
        this.routeService = routeService;
    }

    @GetMapping("")
    public String getAllTariffs(
                            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                            @RequestParam(value = "routeField", defaultValue = "distance", required = false) String routeField,
                            @RequestParam(value = "typeOfRouteSort", defaultValue = "desc", required = false) String typeOfRouteSort,
                            @RequestParam(value = "typeOfTariffSort", defaultValue = "desc", required = false) String typeOfTariffSort,
                            @RequestParam(value = "tariffField", defaultValue = "price", required = false) String tariffField,
                                Model model) {

        model.addAttribute("allRoutes", routeService.findAllRoutes(page, routeField, typeOfRouteSort));
        model.addAttribute("allTariffs", tariffService.getAllTariffs(tariffField, typeOfTariffSort));

        model.addAttribute("totalPages", countTotalAmountOfPage(routeService.getTotalRoutesAmount()));
        model.addAttribute("currentPage", page);
        model.addAttribute("typeOfRouteSort", typeOfRouteSort);
        model.addAttribute("typeOfTariffSort", typeOfTariffSort);
        return "main-page";
    }
}
