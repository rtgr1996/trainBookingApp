package com.trainBookingApp.service;

import com.trainBookingApp.model.ExpensivePricingStrategy;
import com.trainBookingApp.model.NormalPricing;
import com.trainBookingApp.model.PricingStrategy;
import com.trainBookingApp.model.SourceDestination;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class PricingService {

    private final Map<SourceDestination, Double> priceMap;

    private PricingStrategy pricingStrategy;

    public PricingService() {
        priceMap = new HashMap<>();
        initializeDefaultPrice();
        pricingStrategy = new NormalPricing();
    }

    private void initializeDefaultPrice() {
        priceMap.put(new SourceDestination("London", "France"), 20.0);
    }

    public double getPrice(String from, String to, String bookingDate) throws ParseException {
        SourceDestination sourceDestination = new SourceDestination(from, to);
        setPricingStrategyForDate(bookingDate);

        if(priceMap.containsKey(sourceDestination)){
            double price = this.pricingStrategy.calculatePrice(priceMap.get(sourceDestination));
            return price;
        } else {
            throw new RuntimeException("Cannot find trains for the given destination from " + from + " to " + to);
        }
    }

    private void setPricingStrategyForDate(String bookingDateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String currentDateStr = dateFormat.format(new Date());
        Date currentDate = dateFormat.parse(currentDateStr);
        Date bookingDate = dateFormat.parse(bookingDateStr);

        long diffInMillis = bookingDate.getTime() - currentDate.getTime();

        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
        if(diffInDays > 4l){
            this.pricingStrategy = new ExpensivePricingStrategy();
        } else {
            this.pricingStrategy = new NormalPricing();
        }
    }


}
