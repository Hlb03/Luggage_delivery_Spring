package com.luggage_delivery.util;
/*
  User: admin
  Cur_date: 14.11.2022
  Cur_time: 10:06
*/


import java.util.ResourceBundle;

public class PageMaxCountUtil {


    public static long countTotalAmountOfPage(long totalRoutes) {
        ResourceBundle rb = ResourceBundle.getBundle("data-amount");
        int routesPerPage = Integer.parseInt(rb.getString("main-page.route"));

        long maxPage = totalRoutes/routesPerPage;
        if (totalRoutes % routesPerPage != 0)
            maxPage++;

        return maxPage;
    }
}
