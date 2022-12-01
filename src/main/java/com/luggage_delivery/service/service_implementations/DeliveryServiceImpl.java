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
import com.luggage_delivery.repository.RouteRepository;
import com.luggage_delivery.repository.TariffRepository;
import com.luggage_delivery.repository.UserRepository;
import com.luggage_delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static com.luggage_delivery.util.DeliveryPriceCalculateUtil.priceCalculate;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final RouteRepository routeRepository;
    private final TariffRepository tariffRepository;
    private final UserRepository userRepository;

    @Autowired
    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, RouteRepository routeRepository,
                               TariffRepository tariffRepository, UserRepository userRepository) {
        this.deliveryRepository = deliveryRepository;
        this.routeRepository = routeRepository;
        this.tariffRepository = tariffRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void addNewDelivery(double size, double weight, String type,
                               String address, Date deliveryDate, int routeId, BigDecimal price, String userLogin) {
        Route route = routeRepository.getReferenceById(routeId);

        Delivery delivery = new Delivery();
        delivery.setLuggageSize(size);
        delivery.setWeight(weight);
        delivery.setLuggageType(type);
        delivery.setTotalPrice(price);

        delivery.setStartDate(Date.valueOf(LocalDate.now()));
        delivery.setDeliveryDate(deliveryDate);
        delivery.setAddress(address);

        User user = userRepository.getUserByLogin(userLogin);

        delivery.setDeliveryStatus(DeliveryStatus.PROCESSING);
        delivery.setRoute(route);
        delivery.setUser(user);

        System.out.println("DELIVERY PARAMS: " + delivery);
        deliveryRepository.save(delivery);
    }

    @Transactional
    @Override
    public BigDecimal calculateOrderPrice(double size, double weight, int routeId, String option) {
        Route route = routeRepository.getReferenceById(routeId);
        return priceCalculate(weight, size, route.getDistance(), option, tariffRepository.findAll());
    }
}
