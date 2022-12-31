package com.luggage_delivery.reports;

import com.luggage_delivery.entity.Delivery;

import java.util.List;

public interface DeliveryReport {

    List<Delivery> createReport(String fieldToCreateReportOn);
}
