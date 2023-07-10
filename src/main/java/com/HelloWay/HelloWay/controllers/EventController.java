package com.HelloWay.HelloWay.controllers;

import com.HelloWay.HelloWay.entities.*;
import com.HelloWay.HelloWay.repos.ImageRepository;
import com.HelloWay.HelloWay.services.EventService;
import com.HelloWay.HelloWay.services.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    ImageRepository imageRepository;

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
    public ResponseEntity<Party> createParty(@PathVariable Long spaceId, @RequestBody Party party) {
        // Retrieve the space
        Space space = spaceService.findSpaceById(spaceId);

        // Check if the space exists
        if (space == null) {
            return ResponseEntity.notFound().build();
        }

        // Initialize the events list if it's null
        if (space.getEvents() == null) {
            space.setEvents(new ArrayList<>());
        }

        // Set the space for the party
        party.setSpace(space);

        // Save the party
        Party createdParty = eventService.createParty(party);

        // Add the party to the events list
        space.getEvents().add(party);
        spaceService.updateSpace(space);

        return ResponseEntity.ok(createdParty);
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

    @PostMapping("/{id}/images")
    public ResponseEntity<String> addImage(@PathVariable("id") Long id,
                                           @RequestParam("file") MultipartFile file) {
        try {
            Event event = eventService.findEventById(id);

            // Create the Image entity and set the reference to the event entity
            Image image = new Image();
            image.setEvent(event);
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setData(file.getBytes());

            // Persist the Image entity to the database
            imageRepository.save(image);

            return ResponseEntity.ok().body("Image uploaded successfully");
        } catch (IOException ex) {
            throw new RuntimeException("Error uploading file", ex);
        }
    }

}
