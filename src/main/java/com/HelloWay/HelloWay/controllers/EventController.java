package com.HelloWay.HelloWay.controllers;

import com.HelloWay.HelloWay.entities.Event;
import com.HelloWay.HelloWay.entities.Party;
import com.HelloWay.HelloWay.entities.Promotion;
import com.HelloWay.HelloWay.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.findAllEvents();
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable Long eventId) {
        Event event = eventService.findEventById(eventId);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PostMapping("/promotion")
    public Promotion createPromotion(@RequestBody Promotion promotion) {
        return eventService.createPromotion(promotion);
    }

    @PostMapping("/party")
    public Party createParty(@RequestBody Party party) {
        return eventService.createParty(party);
    }

    @GetMapping("/promotions")
    public List<Promotion> getAllPromotions() {
        return eventService.getAllPromotions();
    }

    @GetMapping("/parties")
    public List<Party> getAllParties() {
        return eventService.getAllParties();
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(@PathVariable Long eventId, @RequestBody Event updatedEvent) {
        return eventService.updateEvent(eventId, updatedEvent);
    }

    @GetMapping("/spaces/{spaceId}")
    public List<Event> getEventsBySpaceId(@PathVariable Long spaceId) {
        return eventService.getEventsBySpaceId(spaceId);
    }

    @GetMapping("/date-range")
    public List<Event> getEventsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return eventService.getEventsByDateRange(startDate, endDate);
    }

    @GetMapping("/upcoming")
    public List<Event> getUpcomingEvents(@RequestParam("limit") int limit) {
        return eventService.getUpcomingEvents(limit);
    }

}
