package com.luggage_delivery.repository;

import com.luggage_delivery.entity.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    @Query("SELECT COUNT(r) FROM Route r")
    long countRoutes();

    Page<Route> findAll(Pageable pageable);

    List<Route> findAll();
}
