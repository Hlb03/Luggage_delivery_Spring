package com.luggage_delivery.service.service_implementations;
/*
  User: admin
  Cur_date: 14.11.2022
  Cur_time: 16:39
*/

import com.luggage_delivery.entity.DeliveryStatus;
import com.luggage_delivery.repository.DeliveryStatusRepository;
import com.luggage_delivery.service.DeliveryStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryStatusServiceImpl implements DeliveryStatusService {

    private final DeliveryStatusRepository deliveryStatusRepository;

    @Autowired
    public DeliveryStatusServiceImpl(DeliveryStatusRepository deliveryStatusRepository) {
        this.deliveryStatusRepository = deliveryStatusRepository;
    }


    @Override
    public DeliveryStatus getStatusById(int deliveryStatusId) {
        return deliveryStatusRepository.getReferenceById(deliveryStatusId);
    }
}
