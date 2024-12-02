package com.trainBookingApp.model;

public class NormalPricing implements PricingStrategy{
    @Override
    public double calculatePrice(double price) {
        return price;
    }
}
