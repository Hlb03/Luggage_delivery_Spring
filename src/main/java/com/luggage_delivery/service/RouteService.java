package com.luggage_delivery.service;

import com.luggage_delivery.entity.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RouteService {

    Page<Route> findAllRoutes(int page, String fieldName, String typeOfSort);

    List<Route> findRoutes();
}
