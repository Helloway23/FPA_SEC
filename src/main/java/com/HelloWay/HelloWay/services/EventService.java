package com.HelloWay.HelloWay.services;


import com.HelloWay.HelloWay.entities.Event;
import com.HelloWay.HelloWay.repos.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository ;

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event findEventById(Long id) {
        return eventRepository.findById(id)
                .orElse(null);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
