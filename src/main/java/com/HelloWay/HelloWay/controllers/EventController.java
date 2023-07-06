package com.HelloWay.HelloWay.controllers;

import com.HelloWay.HelloWay.entities.Event;
import com.HelloWay.HelloWay.entities.Party;
import com.HelloWay.HelloWay.entities.Promotion;
import com.HelloWay.HelloWay.entities.Space;
import com.HelloWay.HelloWay.services.EventService;
import com.HelloWay.HelloWay.services.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private SpaceService spaceService;

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

    @PostMapping("/space/{spaceId}")
    public ResponseEntity<?> createEventForSpace(@RequestBody Event event, @PathVariable long spaceId) {
        Space space = spaceService.findSpaceById(spaceId);
        if (space == null){
            return ResponseEntity.badRequest().body("space does not exist with this id " + spaceId);
        }
        event.setSpace(space);
        Event eventObject = eventService.createEvent(event);
        List<Event> events = new ArrayList<>();
        events = space.getEvents();
        events.add(eventObject);
        space.setEvents(events);
        spaceService.updateSpace(space);
        return ResponseEntity.ok().body(eventObject);
    }

    @PostMapping("/promotion/space/{spaceId}")
    public ResponseEntity<?> createPromotionForSpace(@RequestBody Promotion promotion, @PathVariable long spaceId) {
        Space space = spaceService.findSpaceById(spaceId);
        if (space == null){
            return ResponseEntity.badRequest().body("space does not exist with this id " + spaceId);
        }
        promotion.setSpace(space);
        Event eventObject = eventService.createPromotion(promotion);
        List<Event> events = new ArrayList<>();
        events = space.getEvents();
        events.add(eventObject);
        space.setEvents(events);
        spaceService.updateSpace(space);
        return ResponseEntity.ok().body(eventObject);
    }

    @PostMapping("/promotion")
    public Promotion createPromotion(@RequestBody Promotion promotion) {
        return eventService.createPromotion(promotion);
    }

    @PostMapping("/party/space/{spaceId}")
    public ResponseEntity<?> createParty(@RequestBody Party party, @PathVariable long spaceId) {
        Space space = spaceService.findSpaceById(spaceId);
        if (space == null){
            return ResponseEntity.badRequest().body("space does not exist with this id " + spaceId);
        }
        party.setSpace(space);
        Event eventObject = eventService.createParty(party);
        List<Event> events = new ArrayList<>();
        events = space.getEvents();
        events.add(eventObject);
        space.setEvents(events);
        spaceService.updateSpace(space);
        return ResponseEntity.ok().body(eventObject);
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
