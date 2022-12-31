package com.luggage_delivery.reports.impls;
/*
  User: admin
  Cur_date: 29.12.2022
  Cur_time: 12:03
*/

import com.luggage_delivery.entity.Delivery;
import com.luggage_delivery.reports.DeliveryReport;
import com.luggage_delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DateReport implements DeliveryReport {

    private final DeliveryService deliveryService;

    @Autowired
    public DateReport(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Override
    public List<Delivery> createReport(String fieldToCreateReportOn) {
        return deliveryService.getAllByDate(fieldToCreateReportOn);
    }
}
