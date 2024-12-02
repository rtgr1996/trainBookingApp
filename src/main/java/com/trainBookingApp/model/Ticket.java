package com.trainBookingApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Ticket {

    private Long ticketId;

    @NotNull(message = "From location cannot be null")
    private String from;

    @NotNull(message = "To location cannot be null")
    private String to;

    @NotNull(message = "User details cannot be null")
    private User user;

    @NotNull(message = "price cannot be null")
    private double pricePaid;

    private String seat;

    private boolean active = true;

    @NotNull(message = "Booking date cannot be null")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Pattern(regexp = "^\\d{4}/\\d{2}/\\d{2}$", message = "Booking date must be in the format yyyy/MM/dd")
    @Schema(type = "string", example = "2024/12/01", description = "Booking date in yyyy/MM/dd format")
    private String bookingDate;

    private Section section;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(double pricePaid) {
        this.pricePaid = pricePaid;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
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

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Ticket(TickerBuilder tickerBuilder) {
        this.from = tickerBuilder.from;
        this.to =tickerBuilder.to;
        this.user = tickerBuilder.user;
        this.pricePaid = tickerBuilder.pricePaid;
        this.seat = tickerBuilder.seat;
        this.bookingDate = tickerBuilder.bookingDate;
        this.section = tickerBuilder.section;
        this.ticketId = tickerBuilder.id;
    }

    public static class TickerBuilder{
        private Long id;
        private String from;
        private String to;
        private User user;
        private double pricePaid;
        private String seat;
        private String bookingDate;
        private Section section;

        public TickerBuilder setFrom(String from) {
            this.from = from;
            return this;
        }

        public TickerBuilder setTo(String to) {
            this.to = to;
            return this;
        }

        public TickerBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public TickerBuilder setPricePaid(double pricePaid) {
            this.pricePaid = pricePaid;
            return this;
        }

        public TickerBuilder setSeat(String seat) {
            this.seat = seat;
            return this;
        }

        public TickerBuilder setBookingDate(String bookingDate) {
            this.bookingDate = bookingDate;
            return this;
        }

        public TickerBuilder setSection(Section section) {
            this.section = section;
            return this;
        }

        public TickerBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public Ticket build(){
            return new Ticket(this);
        }


    }
}
