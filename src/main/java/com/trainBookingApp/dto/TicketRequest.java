package com.trainBookingApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trainBookingApp.model.Section;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TicketRequest {

    @NotNull(message = "From location cannot be null")
    private String from;

    @NotNull(message = "To location cannot be null")
    private String to;

    @NotNull(message = "First name cannot be null")
    @Size(min = 2, message = "First name must have at least 2 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, message = "Last name must have at least 2 characters")
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Booking date cannot be null")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Pattern(regexp = "^\\d{4}/\\d{2}/\\d{2}$", message = "Booking date must be in the format yyyy/MM/dd")
    @Schema(type = "string", example = "2024/12/01", description = "Booking date in yyyy/MM/dd format")
    private String bookingDate;

    @NotNull(message = "Section cannot be null")
    private Section section;

    public TicketRequest(String from, String to, String firstName, String lastName, String email, String bookingDate, Section section) {
        this.from = from;
        this.to = to;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bookingDate = bookingDate;
        this.section = section;
    }

    public TicketRequest() {
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
}
