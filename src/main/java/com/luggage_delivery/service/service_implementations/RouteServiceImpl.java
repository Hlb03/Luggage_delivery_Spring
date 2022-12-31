package com.luggage_delivery.service.service_implementations;


import com.luggage_delivery.entity.Route;
import com.luggage_delivery.repository.RouteRepository;
import com.luggage_delivery.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ResourceBundle;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public Page<Route> findAllRoutes(int page, String field, String typeOfSort) {
        ResourceBundle rb = ResourceBundle.getBundle("data-amount");
        int routesPerPage = Integer.parseInt(rb.getString("main-page.route"));

        Pageable pg = PageRequest.of(page - 1, routesPerPage,
                typeOfSort.equals("asc") ? Sort.by(field).ascending() : Sort.by(field).descending());
        return routeRepository.findAll(pg);
    }

    @Override
    public List<Route> findRoutes() {
        return routeRepository.findAll();
    }

}
