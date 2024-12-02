package com.trainBookingApp.dto;

import com.trainBookingApp.model.Section;

import java.util.Date;

public class TicketResponse {

    private Long id;
    private String from;
    private String to;
    private String firstName;
    private String lastName;
    private String email;
    private String seat;
    private double pricePaid;
    private String bookingDate;
    private Section section;


    public TicketResponse(TicketResponseBuilder ticketResponseBuilder) {
        this.from = ticketResponseBuilder.from;
        this.to = ticketResponseBuilder.to;
        this.firstName = ticketResponseBuilder.firstName;
        this.lastName = ticketResponseBuilder.lastName;
        this.email = ticketResponseBuilder.email;
        this.seat = ticketResponseBuilder.seat;
        this.pricePaid = ticketResponseBuilder.pricePaid;
        this.bookingDate = ticketResponseBuilder.bookingDate;
        this.section = ticketResponseBuilder.section;
        this.id = ticketResponseBuilder.id;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public double getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(double pricePaid) {
        this.pricePaid = pricePaid;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static class TicketResponseBuilder{

        private String from;
        private String to;
        private String firstName;
        private String lastName;
        private String email;
        private String seat;
        private double pricePaid;
        private String bookingDate;
        private Section section;
        private long id;

        public TicketResponseBuilder setFrom(String from) {
            this.from = from;
            return this;
        }

        public TicketResponseBuilder setTo(String to) {
            this.to = to;
            return this;
        }

        public TicketResponseBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public TicketResponseBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public TicketResponseBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public TicketResponseBuilder setSeat(String seat) {
            this.seat = seat;
            return this;
        }

        public TicketResponseBuilder setPricePaid(double pricePaid) {
            this.pricePaid = pricePaid;
            return this;
        }

        public TicketResponseBuilder setBookingDate(String bookingDate) {
            this.bookingDate = bookingDate;
            return this;
        }

        public TicketResponseBuilder setSection(Section section) {
            this.section = section;
            return this;
        }

        public TicketResponseBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public TicketResponse build(){
            return new TicketResponse(this);
        }
    }

}
