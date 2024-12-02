package com.trainBookingApp.service;

import com.trainBookingApp.dto.TicketRequest;
import com.trainBookingApp.dto.TicketResponse;
import com.trainBookingApp.model.Section;
import com.trainBookingApp.model.SectionData;
import com.trainBookingApp.model.Ticket;
import com.trainBookingApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final LinkedHashMap<Long, Ticket> ticketRepository = new LinkedHashMap<>();
    private long ticketCounter = 1;

    @Autowired
    private PricingService pricingService;

    @Autowired
    private SeatAllocationService seatAllocationService;


    public TicketResponse createTicket(TicketRequest ticketRequest) throws ParseException {
        User user = new User.UserBuilder()
                .setEmail(ticketRequest.getEmail())
                .setFirstName(ticketRequest.getFirstName())
                .setLastName(ticketRequest.getLastName())
                .build();
        
        SectionData sectionData = seatAllocationService.allocateSeat(ticketRequest.getSection(), ticketRequest.getBookingDate());
        
        double price = pricingService.getPrice(ticketRequest.getFrom(), ticketRequest.getTo(), ticketRequest.getBookingDate());
        Ticket ticket = new Ticket.TickerBuilder()
                .setId(ticketCounter)
                .setFrom(ticketRequest.getFrom())
                .setTo(ticketRequest.getTo())
                .setUser(user)
                .setSeat(sectionData.getSeat())
                .setPricePaid(price)
                .setBookingDate(ticketRequest.getBookingDate())
                .setSection(sectionData.getSectionName())
                .setBookingDate(ticketRequest.getBookingDate())
                .build();

        ticketCounter++;

        ticketRepository.put(ticket.getTicketId(), ticket);

        return getTicketResponse(ticket);

    }

    public TicketResponse getTicketDetails(String email, long ticketId) {
        if(!ticketRepository.containsKey(ticketId) || !ticketRepository.get(ticketId).getUser().getEmail().equals(email) || !ticketRepository.get(ticketId).isActive()){
            throw new RuntimeException("Cannot find ticket with given email");
        }
        return getTicketResponse(ticketRepository.get(ticketId));
    }

    public void removeTicket(String email, long ticketId) {
        if(!ticketRepository.containsKey(ticketId) || !ticketRepository.get(ticketId).isActive() || !ticketRepository.get(ticketId).getUser().getEmail().equals(email)){
            throw new RuntimeException("Cannot find ticket with given email");
        }
        Ticket ticket = ticketRepository.get(ticketId);
        ticket.setActive(false);
        seatAllocationService.releaseSeat(ticket.getSection(), ticket.getBookingDate(), ticket.getSeat());
        ticketRepository.remove(ticketId);
    }


    private static TicketResponse getTicketResponse(Ticket ticket) {
        TicketResponse ticketResponse = new TicketResponse.TicketResponseBuilder()
                .setEmail(ticket.getUser().getEmail())
                .setFirstName(ticket.getUser().getFirstName())
                .setLastName(ticket.getUser().getLastName())
                .setSection(ticket.getSection())
                .setSeat(ticket.getSeat())
                .setPricePaid(ticket.getPricePaid())
                .setFrom(ticket.getFrom())
                .setId(ticket.getTicketId())
                .setBookingDate(ticket.getBookingDate())
                .setSection(ticket.getSection())
                .setTo(ticket.getTo()).build();
        return ticketResponse;
    }

    public List<TicketResponse> getAllTickets() {
        if(ticketRepository.isEmpty()){
            throw new RuntimeException("No tickets found yet");
        }
        return ticketRepository.values().stream().filter(Ticket::isActive).map(TicketService::getTicketResponse).collect(Collectors.toList());
    }

    public TicketResponse modifyTicket(String email, String ticketId, String seat, Section section) {
        Ticket ticket = ticketRepository.get(Long.valueOf(ticketId));
        if(!ticket.isActive() && !ticket.getUser().getEmail().equals(email)){
            new RuntimeException("Ticket Id and user email does not match");
        }

        seatAllocationService.allocateSpecificSeat(ticket, seat, section);
        return getTicketResponse(ticket);
    }

    LinkedHashMap<Long, Ticket> getTicketRepository() {
        return ticketRepository;
    }

    public List<TicketResponse> getUserTicketDetails(String email) {
        return ticketRepository.entrySet().stream().filter(f->f.getValue().getUser().getEmail().equals(email) && f.getValue().isActive()).map(m->getTicketResponse(m.getValue())).collect(Collectors.toList());
    }
}
