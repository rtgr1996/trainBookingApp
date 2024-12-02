package com.trainBookingApp.model;

public class ExpensivePricingStrategy implements PricingStrategy{

    @Override
    public double calculatePrice(double price) {
        return price*1.5;
    }
}
