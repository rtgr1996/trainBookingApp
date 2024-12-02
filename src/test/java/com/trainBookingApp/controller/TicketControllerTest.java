package com.trainBookingApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainBookingApp.controller.TicketController;
import com.trainBookingApp.dto.TicketRequest;
import com.trainBookingApp.dto.TicketResponse;
import com.trainBookingApp.model.Section;
import com.trainBookingApp.service.TicketService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class TicketControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testBookTicketSuccess() throws Exception {
        
        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setFrom("London");
        ticketRequest.setTo("France");
        ticketRequest.setFirstName("rohit");
        ticketRequest.setLastName("gaur");
        ticketRequest.setEmail("rohit@example.com");
        ticketRequest.setBookingDate("2024/12/01");
        ticketRequest.setSection(Section.A);

        TicketResponse ticketResponse = new TicketResponse.TicketResponseBuilder()
                .setFrom("London")
                .setTo("France")
                .setFirstName("rohit")
                .setLastName("gaur")
                .setEmail("rohit@example.com")
                .setSeat("1")
                .setPricePaid(100.50)
                .setBookingDate("2024/12/01")
                .setSection(Section.A)
                .setId(1L)
                .build();

        
        Mockito.when(ticketService.createTicket(Mockito.any(TicketRequest.class))).thenReturn(ticketResponse);

        
        mockMvc.perform(post("/api/tickets/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.from").value("London"))
                .andExpect(jsonPath("$.to").value("France"))
                .andExpect(jsonPath("$.firstName").value("rohit"))
                .andExpect(jsonPath("$.lastName").value("gaur"))
                .andExpect(jsonPath("$.email").value("rohit@example.com"))
                .andExpect(jsonPath("$.seat").value("1"));
    }

    @Test
    public void testBookTicketValidationError() throws Exception {
        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setFrom("London");
        ticketRequest.setTo("France");
        ticketRequest.setFirstName("J");
        ticketRequest.setLastName("gaur");
        ticketRequest.setEmail("rohit@example.com");
        ticketRequest.setBookingDate("2024/12/01");
        ticketRequest.setSection(Section.A);

        mockMvc.perform(post("/api/tickets/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").value("First name must have at least 2 characters"));
    }

    @Test
    public void testGetTicketDetailsSuccess() throws Exception {
        
        String email = "rohit@example.com";
        Long ticketId = 1L;
        TicketResponse ticketResponse = new TicketResponse.TicketResponseBuilder()
                .setFrom("London")
                .setTo("France")
                .setFirstName("rohit")
                .setLastName("gaur")
                .setEmail("rohit@example.com")
                .setSeat("1")
                .setPricePaid(100.50)
                .setBookingDate("2024/12/01")
                .setSection(Section.A)
                .setId(ticketId)
                .build();

        
        Mockito.when(ticketService.getTicketDetails(email, ticketId)).thenReturn(ticketResponse);

        
        mockMvc.perform(get("/api/tickets/details/{email}/{ticketId}", email, ticketId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.from").value("London"))
                .andExpect(jsonPath("$.to").value("France"));
    }

    @Test
    public void testRemoveTicketSuccess() throws Exception {
        
        String email = "rohit@example.com";
        Long ticketId = 1L;

        
        Mockito.doNothing().when(ticketService).removeTicket(email, ticketId);

        
        mockMvc.perform(delete("/api/tickets/remove/{email}/{ticketId}", email, ticketId))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    public void testGetAllTicketsSuccess() throws Exception {
        
        TicketResponse ticketResponse = new TicketResponse.TicketResponseBuilder()
                .setFrom("London")
                .setTo("France")
                .setFirstName("rohit")
                .setLastName("gaur")
                .setEmail("rohit@example.com")
                .setSeat("1")
                .setPricePaid(100.50)
                .setBookingDate("2024/12/01")
                .setSection(Section.A)
                .setId(1L)
                .build();

        
        Mockito.when(ticketService.getAllTickets()).thenReturn(List.of(ticketResponse));

        
        mockMvc.perform(get("/api/tickets/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].from").value("London"))
                .andExpect(jsonPath("$[0].to").value("France"));
    }

    @Test
    public void testModifyTicketSuccess() throws Exception {
        
        String email = "rohit@example.com";
        Long ticketId = 1L;
        String section = "B";
        String seat = "1";

        TicketResponse ticketResponse = new TicketResponse.TicketResponseBuilder()
                .setFrom("London")
                .setTo("France")
                .setFirstName("rohit")
                .setLastName("gaur")
                .setEmail("rohit@example.com")
                .setSeat(seat)
                .setPricePaid(120.0)
                .setBookingDate("2024/12/01")
                .setSection(Section.A)
                .setId(ticketId)
                .build();

        
        Mockito.when(ticketService.modifyTicket(email, String.valueOf(ticketId), seat, Section.B)).thenReturn(ticketResponse);

        
        mockMvc.perform(get("/api/tickets/modify/{email}/{ticketId}/{section}/{seat}", email, ticketId, section, seat))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seat").value(seat));
    }
}