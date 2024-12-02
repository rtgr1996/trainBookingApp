package com.trainBookingApp.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeatManagerTest {

    @Mock
    private Set<Integer> vacantSeats;

    @Mock
    private Set<Integer> allocatedSeats;

    private SeatManager seatManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);  // Initialize mocks
        seatManager = new SeatManager(100);  // SeatManager with capacity of 100

        for (int i = 1; i <= 100; i++) {
            when(vacantSeats.contains(i)).thenReturn(true);  // All seats initially vacant
        }
    }

    @Test
    public void testAllocateSpecificSeat_success() {
        // Arrange
        String oldSeat = "10";
        String newSeat = "20";

        when(allocatedSeats.contains(Integer.parseInt(newSeat))).thenReturn(true);
        when(vacantSeats.contains(Integer.parseInt(newSeat))).thenReturn(false);
        when(allocatedSeats.contains(Integer.parseInt(oldSeat))).thenReturn(false);
        when(vacantSeats.contains(Integer.parseInt(oldSeat))).thenReturn(true);

        boolean result = seatManager.allocateSpecificSeat(oldSeat, newSeat);

        assertTrue(result);
        assertFalse(vacantSeats.contains(Integer.parseInt(newSeat)));  // Verify new seat is taken from vacant
        assertTrue(allocatedSeats.contains(Integer.parseInt(newSeat)));  // Verify new seat is added to allocated
        assertFalse(allocatedSeats.contains(Integer.parseInt(oldSeat)));  // Verify old seat is removed from allocated
        assertTrue(vacantSeats.contains(Integer.parseInt(oldSeat)));   // Verify old seat is added back to vacant
    }

    @Test
    public void testAllocateSpecificSeat_seatAlreadyAllocated() {
        // Arrange
        String oldSeat = "10";
        String newSeat = "200";

        try {
            seatManager.allocateSpecificSeat(oldSeat, newSeat);
            fail("Expected RuntimeException not thrown");
        } catch (RuntimeException e) {
            assertEquals("Asked seat is either empty or already allocated to someone else", e.getMessage());
        }
    }

    @Test
    public void testAllocateSpecificSeat_exceedsCapacity() {
        String oldSeat = "10";
        String newSeat = "150";  // Exceeds the capacity of 100

        try {
            seatManager.allocateSpecificSeat(oldSeat, newSeat);
            fail("Expected RuntimeException not thrown");
        } catch (RuntimeException e) {
            assertEquals("Asked seat is either empty or already allocated to someone else", e.getMessage());
        }
    }
}