package com.trainBookingApp.service;

import com.trainBookingApp.model.SeatManager;
import com.trainBookingApp.model.Section;
import com.trainBookingApp.model.SectionData;
import com.trainBookingApp.model.Ticket;
import org.springframework.stereotype.Service;

@Service
public class SeatAllocationService {

    public SectionData allocateSeat(Section section, String date){
        SeatManager seatManager = section.getSeatAllocationsByDate(date);
        String allocatedSeat = seatManager.allocateSeat();
        if(allocatedSeat == null){
            Section newSection = section == Section.A ? Section.B : Section.A;
            seatManager = newSection.getSeatAllocationsByDate(date);
            allocatedSeat = seatManager.allocateSeat();
            if(allocatedSeat == null){
                throw new RuntimeException("Section is full cannot book more seats");
            }
            return createSectionData(newSection, allocatedSeat, date);
        }

        return createSectionData(section, allocatedSeat, date);

    }


    private SectionData createSectionData(Section section, String allocatedSeat, String date) {
        SectionData sectionData = new SectionData(section, allocatedSeat, date);
        return sectionData;
    }

    public void releaseSeat(Section section, String date, String seat){
        SeatManager seatManager = section.getSeatAllocationsByDate(date);
        seatManager.releaseSeat(seat);
    }

    public void allocateSpecificSeat(Ticket ticket, String newSeat, Section section) {
        String date = ticket.getBookingDate();

        if(ticket.getSection().getName().equals(section.getName())){ // same section
            SeatManager seatManager = section.getSeatAllocationsByDate(date);
            seatManager.allocateSpecificSeat(ticket.getSeat(), newSeat);
        } else {
            SeatManager seatManager = section.getSeatAllocationsByDate(date);

            Section newSection = ticket.getSection() == Section.A ? Section.B : Section.A;
            SeatManager newSeatManager = newSection.getSeatAllocationsByDate(date);
            newSeatManager.allocateSpecificSeat(newSeat);
            ticket.setSection(newSection);
            seatManager.releaseSeat(ticket.getSeat());
        }
        ticket.setSeat(newSeat);
    }
}
