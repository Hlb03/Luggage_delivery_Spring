package com.luggage_delivery.service;

import java.math.BigDecimal;
import java.sql.Date;

public interface DeliveryService {

    void addNewDelivery(double size, double weight, String type, String address,
                        Date deliveryDate, int routeId, BigDecimal price, String userLogin);
    BigDecimal calculateOrderPrice(double size, double weight, int routeId, String option);
}
