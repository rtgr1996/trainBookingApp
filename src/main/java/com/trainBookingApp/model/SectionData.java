package com.trainBookingApp.model;

public class SectionData {
    private Section section;
    private String seat;
    private String bookingDate;

    public SectionData(Section section, String seat, String bookingDate) {
        this.section = section;
        this.seat = seat;
        this.bookingDate = bookingDate;
    }

    public Section getSectionName() {
        return section;
    }

    public void setSectionName(Section sectionName) {
        this.section = sectionName;
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
}
