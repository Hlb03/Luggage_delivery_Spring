package com.luggage_delivery.service;

import com.luggage_delivery.entity.Delivery;

import java.sql.Date;

public interface DeliveryService {

    void addNewDelivery(double size, double weight, String type, String address, Date deliveryDate, int routeId, String option);
}
