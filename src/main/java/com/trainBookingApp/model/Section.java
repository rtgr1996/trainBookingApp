package com.trainBookingApp.model;

import java.util.HashMap;
import java.util.Map;

public enum Section {
    A("Section A", 50),
    B("Section B", 50);

    private final String name;
    private final int capacity;
    private final Map<String, SeatManager> seatAllocationsByDate;


    Section(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.seatAllocationsByDate = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public SeatManager getSeatAllocationsByDate(String date) {
        return seatAllocationsByDate.computeIfAbsent(date, d->new SeatManager(capacity));
    }
}
