package com.trainBookingApp.dto;

public class StationResponseDto {
    private String to;
    private String from;
    private double price;

    public StationResponseDto(String to, String from, double price) {
        this.to = to;
        this.from = from;
        this.price = price;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
