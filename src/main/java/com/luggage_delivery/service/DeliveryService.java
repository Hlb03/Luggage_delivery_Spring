package com.luggage_delivery.service;

import com.luggage_delivery.entity.Delivery;
import com.luggage_delivery.entity.DeliveryStatus;
import com.luggage_delivery.entity.User;
import com.luggage_delivery.exceptions.LackOfBalanceException;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public interface DeliveryService {

    Delivery getDeliveryById(int id);

    void addNewDelivery(double size, double weight, String type, String address,
                        Date deliveryDate, int routeId, BigDecimal price, String userLogin);

    BigDecimal calculateOrderPrice(double size, double weight, int routeId, String option);

    Page<Delivery> getUserDeliveries(User user, int page);

    Page<Delivery> getAllDeliveries(int page, DeliveryStatus status);

    void payOrder(int orderId, String userLogin) throws LackOfBalanceException;

    void changeStatus(int orderId, String typeOfProcess);

    List<Delivery> getAllByDate(String date);

    List<Delivery> getAllByRoute(int routeId);
}
