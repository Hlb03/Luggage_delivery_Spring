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
import com.luggage_delivery.exceptions.LackOfBalanceException;
import com.luggage_delivery.repository.DeliveryRepository;
import com.luggage_delivery.repository.RouteRepository;
import com.luggage_delivery.repository.TariffRepository;
import com.luggage_delivery.repository.UserRepository;
import com.luggage_delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

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

    @Override
    public Delivery getDeliveryById(int id) {
        return deliveryRepository.getReferenceById(id);
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

        deliveryRepository.save(delivery);
    }

    @Transactional
    @Override
    public BigDecimal calculateOrderPrice(double size, double weight, int routeId, String option) {
        Route route = routeRepository.getReferenceById(routeId);
        return priceCalculate(weight, size, route.getDistance(), option, tariffRepository.findAll());
    }

    @Override
    public Page<Delivery> getUserDeliveries(User user, int page) {
        ResourceBundle rb = ResourceBundle.getBundle("data-amount");
        int orderPerPage = Integer.parseInt(rb.getString("user.orders"));

        Pageable pageable = PageRequest.of(page - 1, orderPerPage, Sort.Direction.ASC, "deliveryStatus");
        return deliveryRepository.findAllByUser(user, pageable);
    }

    @Override
    public Page<Delivery> getAllDeliveries(int page, DeliveryStatus status) {
        ResourceBundle rb = ResourceBundle.getBundle("data-amount");
        int orderPerPage = Integer.parseInt(rb.getString("manager.orders"));

        Pageable pageable = PageRequest.of(page - 1, orderPerPage, Sort.Direction.ASC, "id");
        return deliveryRepository.findAllByDeliveryStatus(status, pageable);
    }


    @Transactional
    @Override
    public void payOrder(int orderId, String userLogin) throws LackOfBalanceException {
        Delivery delivery = deliveryRepository.getReferenceById(orderId);
        User user = userRepository.getUserByLogin(userLogin);

        if ((user.getBalance().subtract(delivery.getTotalPrice()).compareTo(new BigDecimal("0")) < 0)) {
            throw new LackOfBalanceException("Lack of balance. Please replenish it");
        }
        user.setBalance(user.getBalance().subtract(delivery.getTotalPrice()));
        delivery.setDeliveryStatus(DeliveryStatus.IN_PROGRESS);

        deliveryRepository.save(delivery);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void changeStatus(int orderId, String typeOfProcess) {
        Delivery delivery = deliveryRepository.getReferenceById(orderId);
        if (typeOfProcess.equals("approve")) {
            delivery.setDeliveryStatus(DeliveryStatus.PAY);
        }
        else {
            delivery.setDeliveryStatus(DeliveryStatus.REJECTED);
        }
    }

    @Override
    public List<Delivery> getAllByDate(String date) {
        return deliveryRepository.findAllByStartDateOrDeliveryDate(Date.valueOf(date), Date.valueOf(date));
    }

    @Override
    public List<Delivery> getAllByRoute(int routeId) {
        Route route = routeRepository.getReferenceById(routeId);
        return deliveryRepository.findAllByRoute(route);
    }


}
