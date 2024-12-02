package com.trainBookingApp.service;

import com.trainBookingApp.dto.TicketRequest;
import com.trainBookingApp.dto.TicketResponse;
import com.trainBookingApp.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService; // Service to be tested

    @Mock
    private PricingService pricingService; // Mocked PricingService

    @Mock
    private SeatAllocationService seatAllocationService; // Mocked SeatAllocationService

    @Mock
    private SeatManager seatManagerMock; // Mock SeatManager

    @Mock
    private SectionData sectionDataMock; // Mock SectionData

    @Mock
    private TicketRequest ticketRequestMock; // Mock TicketRequest

    @Mock
    private Ticket ticketMock; // Mock Ticket

    @Mock
    private User userMock; // Mock User

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTicket() throws ParseException {
        when(ticketRequestMock.getEmail()).thenReturn("test@example.com");
        when(ticketRequestMock.getFirstName()).thenReturn("John");
        when(ticketRequestMock.getLastName()).thenReturn("Doe");
        when(ticketRequestMock.getFrom()).thenReturn("London");
        when(ticketRequestMock.getTo()).thenReturn("France");
        when(ticketRequestMock.getSection()).thenReturn(Section.A);
        when(ticketRequestMock.getBookingDate()).thenReturn("2024/12/01");

        when(seatAllocationService.allocateSeat(Section.A, "2024/12/01")).thenReturn(sectionDataMock);
        when(sectionDataMock.getSeat()).thenReturn("1");
        when(sectionDataMock.getSectionName()).thenReturn(Section.A);

        when(pricingService.getPrice("London", "France", "2024/12/01")).thenReturn(20.0);


        TicketResponse ticketResponse = ticketService.createTicket(ticketRequestMock);

        verify(seatAllocationService).allocateSeat(Section.A, "2024/12/01");
        verify(pricingService).getPrice("London", "France", "2024/12/01");

        assertNotNull(ticketResponse);
        assertEquals("London", ticketResponse.getFrom());
        assertEquals("France", ticketResponse.getTo());
        assertEquals("test@example.com", ticketResponse.getEmail());
        assertEquals(20.0, ticketResponse.getPricePaid(), 0);
    }

    @Test
    public void testGetTicketDetails() {
        // Setup
        Ticket ticketMock = mock(Ticket.class);
        when(ticketMock.getTicketId()).thenReturn(1L);
        when(ticketMock.isActive()).thenReturn(true);
        when(ticketMock.getUser()).thenReturn(userMock);
        when(userMock.getEmail()).thenReturn("test@example.com");

        ticketService.getTicketRepository().put(1L, ticketMock); // Mocking ticket repository

        // Test
        TicketResponse ticketResponse = ticketService.getTicketDetails("test@example.com", 1L);

        // Verifying the result
        assertNotNull(ticketResponse);
        assertEquals(1L, ticketResponse.getId());
        assertEquals("test@example.com", ticketResponse.getEmail());
    }

    @Test
    public void testRemoveTicket() {
        Ticket ticketMock = mock(Ticket.class);
        when(ticketMock.isActive()).thenReturn(true);
        when(ticketMock.getUser()).thenReturn(userMock);
        when(userMock.getEmail()).thenReturn("test@example.com");

        ticketService.getTicketRepository().put(1L, ticketMock);

        doNothing().when(seatAllocationService).releaseSeat(any(), any(), any());

        ticketService.removeTicket("test@example.com", 1L);

        assertFalse(ticketService.getTicketRepository().containsKey(1L));
    }

    @Test
    public void testGetAllTickets() {
        Ticket activeTicket = mock(Ticket.class);
        when(activeTicket.isActive()).thenReturn(true);
        when(activeTicket.getTicketId()).thenReturn(1L);
        when(activeTicket.getUser()).thenReturn(userMock);
        when(userMock.getEmail()).thenReturn("test@example.com");

        ticketService.getTicketRepository().put(1L, activeTicket); // Adding mock ticket

        List<TicketResponse> tickets = ticketService.getAllTickets();

        assertNotNull(tickets);
        assertEquals(1, tickets.size());
        assertEquals("test@example.com", tickets.get(0).getEmail());
    }


}