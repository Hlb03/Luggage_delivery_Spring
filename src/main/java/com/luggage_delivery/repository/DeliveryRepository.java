package com.luggage_delivery.repository;

import com.luggage_delivery.entity.Delivery;
import com.luggage_delivery.entity.DeliveryStatus;
import com.luggage_delivery.entity.Route;
import com.luggage_delivery.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    Page<Delivery> findAllByUser(User user, Pageable pageable);

    Page<Delivery> findAllByDeliveryStatus(DeliveryStatus status, Pageable pageable);

    List<Delivery> findAllByStartDateOrDeliveryDate(Date date1, Date date2);

    List<Delivery> findAllByRoute(Route route);
}
