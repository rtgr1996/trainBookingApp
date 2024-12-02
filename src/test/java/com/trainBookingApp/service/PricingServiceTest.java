package com.trainBookingApp.service;

import com.trainBookingApp.model.ExpensivePricingStrategy;
import com.trainBookingApp.model.NormalPricing;
import com.trainBookingApp.model.PricingStrategy;
import com.trainBookingApp.model.SourceDestination;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PricingServiceTest {

    private PricingService pricingService;

    @Mock
    private PricingStrategy normalPricingStrategyMock;

    @Mock
    private PricingStrategy expensivePricingStrategyMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        pricingService = new PricingService();
    }

    @Test
    public void testGetPrice_normalPricing() throws ParseException {
        // Arrange
        String from = "London";
        String to = "France";
        String bookingDate = "2024/12/03"; // Less than 4 days from today

        // Act
        double price = pricingService.getPrice(from, to, bookingDate);

        // Assert
        assertEquals(20.0, price, 0.01);
    }

    @Test
    public void testGetPrice_expensivePricing() throws ParseException {
        // Arrange
        String from = "London";
        String to = "France";
        String bookingDate = "2024/12/10"; // More than 4 days from today

        pricingService = spy(pricingService);
        ExpensivePricingStrategy expensiveStrategyMock = mock(ExpensivePricingStrategy.class);
        doReturn(30.0).when(expensiveStrategyMock).calculatePrice(20.0);

        // Act
        double price = pricingService.getPrice(from, to, bookingDate);

        // Assert
        assertTrue(price > 20.0); // Assuming ExpensivePricing increases the price
    }

    @Test(expected = RuntimeException.class)
    public void testGetPrice_destinationNotFound() throws ParseException {
        // Arrange
        String from = "Unknown";
        String to = "Nowhere";
        String bookingDate = "2024/12/05";

        // Act
        pricingService.getPrice(from, to, bookingDate);
    }

    @Test
    public void testSetPricingStrategyForDate_normalPricing() throws ParseException {
        // Arrange
        String bookingDate = "2024/12/02"; // Close date (within 4 days)

        // Act
        pricingService.getPrice("London", "France", bookingDate); // Triggers strategy set

        // Assert
        assertTrue(pricingService.getPrice("London", "France", bookingDate) > 0);
    }
}
