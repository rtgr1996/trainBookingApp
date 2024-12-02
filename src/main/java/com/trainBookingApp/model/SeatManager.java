package com.trainBookingApp.model;

import java.util.HashSet;
import java.util.Set;

public class SeatManager {
    private final int capacity;
    private final Set<Integer> vacantSeats;
    private final Set<Integer> allocatedSeats;

    public SeatManager(int capacity) {
        this.capacity = capacity;
        vacantSeats = new HashSet<>();
        allocatedSeats = new HashSet<>();

        for(int i =1;i<=capacity;i++){
            vacantSeats.add(i);
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public Set<Integer> getVacantSeats() {
        return vacantSeats;
    }

    public Set<Integer> getAllocatedSeats() {
        return allocatedSeats;
    }

    public String allocateSeat(){
        if(vacantSeats.isEmpty()){
            return null;
        }
        int seat = vacantSeats.iterator().next();
        vacantSeats.remove(seat);
        allocatedSeats.add(seat);
        return String.valueOf(seat);
    }

    public boolean releaseSeat(String seat){
        int seatId = Integer.parseInt(seat);
        if(!allocatedSeats.contains(seatId)){
            return false;
        }
        allocatedSeats.remove(seatId);
        vacantSeats.add(seatId);
        return true;
    }

    public boolean allocateSpecificSeat(String seat, String newSeat) {
        int oldSeatId = Integer.parseInt(seat);
        int newSeatId = Integer.parseInt(newSeat);

        if(newSeatId > capacity || allocatedSeats.contains(newSeatId)){
            throw new RuntimeException("Asked seat is either empty or already allocated to someone else");
        }
        vacantSeats.remove(newSeatId);
        allocatedSeats.add(newSeatId);

        allocatedSeats.remove(oldSeatId);
        vacantSeats.add(oldSeatId);
        return true;
    }

    public void allocateSpecificSeat(String newSeat) {
        int seatId = Integer.parseInt(newSeat);

        if(allocatedSeats.contains(seatId)){
            throw new RuntimeException("Asked seat is already allocated to someone else");
        }

        vacantSeats.remove(seatId);
        allocatedSeats.add(seatId);

    }
}
