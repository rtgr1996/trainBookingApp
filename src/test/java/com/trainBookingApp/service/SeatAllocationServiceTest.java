package com.trainBookingApp.service;

import com.trainBookingApp.model.SeatManager;
import com.trainBookingApp.model.Section;
import com.trainBookingApp.model.SectionData;
import com.trainBookingApp.model.Ticket;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SeatAllocationServiceTest {

    @InjectMocks
    private SeatAllocationService seatAllocationService;

    @Mock
    private SeatManager seatManagerMock;

    @Mock
    private SeatManager fallbackSeatManagerMock;

    @Mock
    private Section sectionMock;

    @Mock
    private Section sectionBMock;

    @Mock
    private Ticket ticketMock;

    @Mock
    private SectionData sectionDataMock;

    public SeatAllocationServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAllocateSeat_sectionA_hasAvailableSeats() {
        //
        String date = "2024/12/01";
        String allocatedSeat = "1";
        when(sectionMock.getSeatAllocationsByDate(date)).thenReturn(seatManagerMock);
        when(seatManagerMock.allocateSeat()).thenReturn(allocatedSeat);
        when(seatManagerMock.allocateSeat()).thenReturn("1");

        // Act
        SectionData sectionData = seatAllocationService.allocateSeat(sectionMock, date);

        // Assert
        assertNotNull(sectionData);
        assertEquals(allocatedSeat, sectionData.getSeat());
        assertEquals(sectionMock, sectionData.getSectionName());
    }

    @Test
    public void testAllocateSeat_sectionA_full_switchToSectionB() {
        // Arrange
        String date = "2024/12/01";
        when(sectionMock.getSeatAllocationsByDate(date)).thenReturn(seatManagerMock);
        when(seatManagerMock.allocateSeat()).thenReturn(null); // Simulate full section
        when(sectionBMock.getSeatAllocationsByDate(date)).thenReturn(seatManagerMock);
        when(seatManagerMock.allocateSeat()).thenReturn("2"); // Section B has an available seat

        // Act
        SectionData sectionData = seatAllocationService.allocateSeat(sectionMock, date);

        // Assert
        assertNotNull(sectionData);
        assertEquals("2", sectionData.getSeat());
        assertEquals(sectionMock, sectionData.getSectionName());
    }

    @Test
    public void testReleaseSeat() {
        // Arrange
        String seat = "1";
        String date = "2024/12/01";
        when(sectionMock.getSeatAllocationsByDate(date)).thenReturn(seatManagerMock);

        // Act
        seatAllocationService.releaseSeat(sectionMock, date, seat);

        // Assert
        verify(seatManagerMock, times(1)).releaseSeat(seat);
    }

    @Test
    public void testAllocateSpecificSeat_sameSection() {
        String newSeat = "2";
        String oldSeat = "1";
        String date = "2024/12/01";
        when(ticketMock.getBookingDate()).thenReturn(date);
        when(ticketMock.getSeat()).thenReturn(oldSeat);
        when(ticketMock.getSection()).thenReturn(Section.A);
        when(sectionMock.getSeatAllocationsByDate(date)).thenReturn(seatManagerMock);

        // Act
        seatAllocationService.allocateSpecificSeat(ticketMock, newSeat, Section.A);

        // Assert
        verify(ticketMock, times(1)).setSeat(newSeat); // Ensure ticket seat was updated
        System.out.println("allocateSpecificSeat was invoked successfully.");
    }
}
