package com.luggage_delivery.service.service_implementations;
/*
  User: admin
  Cur_date: 14.11.2022
  Cur_time: 16:54
*/

import com.luggage_delivery.entity.Delivery;
import com.luggage_delivery.entity.DeliveryStatus;
import com.luggage_delivery.entity.Route;
import com.luggage_delivery.entity.User;
import com.luggage_delivery.repository.DeliveryRepository;
import com.luggage_delivery.repository.DeliveryStatusRepository;
import com.luggage_delivery.repository.RouteRepository;
import com.luggage_delivery.repository.TariffRepository;
import com.luggage_delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static com.luggage_delivery.util.DeliveryPriceCalculateUtil.priceCalculate;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryStatusRepository deliveryStatusRepository;
    private final RouteRepository routeRepository;
    private final TariffRepository tariffRepository;

    @Autowired
    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, DeliveryStatusRepository deliveryStatusRepository,
                               RouteRepository routeRepository, TariffRepository tariffRepository) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryStatusRepository = deliveryStatusRepository;
        this.routeRepository = routeRepository;
        this.tariffRepository = tariffRepository;
    }

    @Override
    public void addNewDelivery(double size, double weight, String type,
                               String address, Date deliveryDate, int routeId, String option) {
        DeliveryStatus deliveryStatus = deliveryStatusRepository.getByStatusName("PROCESSING");
        Route route = routeRepository.getReferenceById(routeId);

        Delivery delivery = new Delivery();
        delivery.setLuggageSize(size);
        delivery.setWeight(weight);
        delivery.setLuggageType(type);
        delivery.setTotalPrice(priceCalculate(weight, size, route.getDistance(), option, tariffRepository.findAll()));

        delivery.setStartDate(Date.valueOf(LocalDate.now()));
        delivery.setDeliveryDate(deliveryDate);
        delivery.setAddress(address);

        User user = new User();
        user.setId(2);

        delivery.setStatus(deliveryStatus);
        delivery.setRoute(route);
        delivery.setUser(user);

        System.out.println("DELIVERY PARAMS: " + delivery);
//        deliveryRepository.save(delivery);
    }

    @Override
    public BigDecimal calculateOrderPrice(double size, double weight, int routeId, String option) {
        Route route = routeRepository.getReferenceById(routeId);
        return priceCalculate(weight, size, route.getDistance(), option, tariffRepository.findAll());
    }
}
