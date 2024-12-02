package com.trainBookingApp.controller;

import com.trainBookingApp.dto.TicketRequest;
import com.trainBookingApp.dto.TicketResponse;
import com.trainBookingApp.model.Section;
import com.trainBookingApp.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/book")
    public ResponseEntity<?> bookTicket(@Valid @RequestBody TicketRequest ticketRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            TicketResponse ticketResponse = ticketService.createTicket(ticketRequest);
            return ResponseEntity.ok(ticketResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/details/{email}/{ticketId}")
    public ResponseEntity<?> getTicketDetails(@PathVariable String email, @PathVariable String ticketId){
        try {
            TicketResponse ticketResponse = ticketService.getTicketDetails(email, Long.valueOf(ticketId));
            return ResponseEntity.ok(ticketResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/userDetail/{email}")
    public ResponseEntity<?> getUserTicketDetails(@PathVariable String email){
        try {
            List<TicketResponse> ticketResponse = ticketService.getUserTicketDetails(email);
            return ResponseEntity.ok(ticketResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remove/{email}/{ticketId}")
    public ResponseEntity<?> removeTicket(@PathVariable String email, @PathVariable String ticketId){
        try {
            ticketService.removeTicket(email, Long.valueOf(ticketId));
            return ResponseEntity.ok("Success");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllTickets(){
        List<TicketResponse> ticketResponseList;
        try {
            ticketResponseList = ticketService.getAllTickets();
            return ResponseEntity.ok(ticketResponseList);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/modify/{email}/{ticketId}/{section}/{seat}")
    public ResponseEntity<?> modifyTicket(@PathVariable String email, @PathVariable String ticketId, @PathVariable Section section, @PathVariable String seat ){
        try {
            TicketResponse ticketResponse =  ticketService.modifyTicket(email, ticketId, seat, section);
            return ResponseEntity.ok(ticketResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
