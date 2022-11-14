package com.luggage_delivery.service;


import com.luggage_delivery.entity.Tariff;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TariffService {
    List<Tariff> getAllTariffs(String field, String typeOfSort);
}
