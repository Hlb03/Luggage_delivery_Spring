package com.luggage_delivery.service.service_implementations;


import com.luggage_delivery.entity.Tariff;
import com.luggage_delivery.repository.TariffRepository;
import com.luggage_delivery.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;

    @Autowired
    public TariffServiceImpl(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    @Override
    public List<Tariff> getAllTariffs(String field, String typeOfOrder) {

        return tariffRepository.findAll(Sort.by(Sort.Direction.fromString(typeOfOrder), field));
    }
}
