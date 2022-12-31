package com.luggage_delivery.util;
/*
  User: admin
  Cur_date: 14.11.2022
  Cur_time: 17:23
*/

import com.luggage_delivery.entity.Tariff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryPriceCalculateUtil {

    private final static Logger LOG = LoggerFactory.getLogger(DeliveryPriceCalculateUtil.class);

    public static BigDecimal priceCalculate(double weight, double size, double distance, String option, List<Tariff> tariffs) {
        BigDecimal price = new BigDecimal("0");
        Map<String, BigDecimal> typeAndPriceOfTariffs = getTariffPriceByName(tariffs);

        price = price.add(new BigDecimal(weight).multiply(typeAndPriceOfTariffs.get("WEIGHT")));
        price = price.add(new BigDecimal(size).multiply(typeAndPriceOfTariffs.get("SIZE")));
        price = price.add(new BigDecimal(distance).multiply(typeAndPriceOfTariffs.get("DISTANCE")));

        if ("Fragile".equals(option))
            price = price.multiply(typeAndPriceOfTariffs.get("FRAGILE"));

        if (price.toString().split("\\.")[1].length() > 2) {
            price = price.add(new BigDecimal("0.01"));
            LOG.debug("PRICE AFTER ADDING 0.01 IS " + price);
            price = new BigDecimal(price.toString().substring(0,
                    price.toString().length() - cutExtraSymbolsInPrice(price.toString())));
        }


        return price;
    }

    private static Map<String, BigDecimal> getTariffPriceByName(List<Tariff> allTariffs) {
        Map<String, BigDecimal> tariffNameAndPrice = new HashMap<>();
        allTariffs.forEach(tariff -> tariffNameAndPrice.put(tariff.getType(), tariff.getPrice()));

        return tariffNameAndPrice;
    }

    private static int cutExtraSymbolsInPrice(String price) {
        int amountOfSymbols = 0;
        int amountOfSymbolAfterDotInPrice = price.split("\\.")[1].length();

        for (; amountOfSymbols < amountOfSymbolAfterDotInPrice - 2; amountOfSymbols++) {}

        LOG.debug(amountOfSymbols + " SYMBOLS TO CUT OFF FROM GENERATED PRICE");
        return amountOfSymbols;
    }
}
