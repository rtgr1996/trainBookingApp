package com.trainBookingApp.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class User {

    @NotNull(message =  "First name cannot be null")
    @Size(min = 2,message =  "First name must have at least 2 characters" )
    private String firstName;

    @NotNull(message =  "Last name cannot be null")
    @Size(min = 2,message =  "Last name must have at least 2 characters" )
    private String lastName;

    @Email(message = "Invalid email address")
    private String email;

    public User(UserBuilder userBuilder) {
        this.email = userBuilder.email;
        this.firstName = userBuilder.firstName;
        this.lastName = userBuilder.lastName;
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

    public static class UserBuilder{
        private String firstName;
        private String lastName;
        private String email;

        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }
}
