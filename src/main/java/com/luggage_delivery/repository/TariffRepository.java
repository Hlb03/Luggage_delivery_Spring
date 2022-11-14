package com.luggage_delivery.repository;

import com.luggage_delivery.entity.Tariff;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Integer> {

    List<Tariff> findAll(Sort sort);
}
