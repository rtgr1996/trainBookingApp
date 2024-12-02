package com.trainBookingApp.model;

import java.util.Objects;

public class SourceDestination {
    private String from;
    private String to;

    public SourceDestination(String from, String to) {
        this.from = from;
        this.to = to;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SourceDestination source = (SourceDestination) o;
        return from.equals(source.from) && to.equals(source.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
