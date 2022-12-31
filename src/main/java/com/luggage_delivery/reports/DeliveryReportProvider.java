package com.luggage_delivery.reports;
/*
  User: admin
  Cur_date: 29.12.2022
  Cur_time: 12:15
*/

import com.luggage_delivery.reports.impls.DateReport;
import com.luggage_delivery.reports.impls.RouteReport;
import org.springframework.stereotype.Component;

@Component
public class DeliveryReportProvider {

    private final RouteReport routeReport;
    private final DateReport dateReport;

    public DeliveryReportProvider(RouteReport routeReport, DateReport dateReport) {
        this.routeReport = routeReport;
        this.dateReport = dateReport;
    }

    public DeliveryReport getReportInstance(String param) {
        return param.equals("Route") ? routeReport : dateReport;
    }
}
