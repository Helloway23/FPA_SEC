package com.HelloWay.HelloWay.controllers;

import com.HelloWay.HelloWay.entities.EReservation;
import com.HelloWay.HelloWay.entities.Reservation;
import com.HelloWay.HelloWay.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.findAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.findReservationById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/space/{spaceId}/user/{userId}")
    public ResponseEntity<Reservation> createReservation(
            @RequestBody Reservation reservation,
            @PathVariable Long spaceId,
            @PathVariable Long userId
    ) {
        Reservation createdReservation = reservationService.createReservationForSpaceAndUser(reservation, spaceId, userId);
        return ResponseEntity.ok(createdReservation);
    }

    @PostMapping("/space/{spaceId}/user/{userId}/board/boardId")
    public ResponseEntity<Reservation> createReservationWithBoard(
            @RequestBody Reservation reservation,
            @PathVariable Long spaceId,
            @PathVariable Long userId,
            @PathVariable Long boardId
    ) {
        Reservation createdReservation = reservationService.createReservationForSpaceAndUserWithBoard(reservation, spaceId, userId, boardId);
        return ResponseEntity.ok(createdReservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable Long id,
            @RequestBody Reservation reservation
    ) {
        Reservation existingReservation = reservationService.findReservationById(id);
        if (existingReservation == null) {
            return ResponseEntity.notFound().build();
        }
        reservation.setIdReservation(id);
        Reservation updatedReservation = reservationService.updateReservation(reservation);
        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.findReservationById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        reservationService.deleteReservation(id);
        return ResponseEntity.ok("Reservation deleted successfully");
    }

    @GetMapping("/space/{spaceId}")
    public ResponseEntity<List<Reservation>> getReservationsBySpaceId(@PathVariable Long spaceId) {
        List<Reservation> reservations = reservationService.findReservationsBySpaceId(spaceId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.findReservationsByUserId(userId);
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<Reservation> acceptReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.findReservationById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the reservation status to "ACCEPTED" (assuming you have an appropriate enumeration for status)
        reservation.setStatus(EReservation.CONFIRMED);

        Reservation updatedReservation = reservationService.updateReservation(reservation);
        return ResponseEntity.ok(updatedReservation);
    }

    @PostMapping("/{id}/refuse")
    public ResponseEntity<Reservation> refuseReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.findReservationById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the reservation status to "REFUSED" (assuming you have an appropriate enumeration for status)
        reservation.setStatus(EReservation.REFUSED);

        Reservation updatedReservation = reservationService.updateReservation(reservation);
        return ResponseEntity.ok(updatedReservation);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Reservation> cancelReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.findReservationById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the reservation status to "CANCELED" (assuming you have an appropriate enumeration for status)
        reservation.setStatus(EReservation.CANCELED);

        Reservation updatedReservation = reservationService.updateReservation(reservation);
        return ResponseEntity.ok(updatedReservation);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Reservation>> getReservationsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Reservation> reservations = reservationService.findReservationsByDateRange(startDate, endDate);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Reservation>> getUpcomingReservations(@RequestParam("limit") int limit) {
        List<Reservation> reservations = reservationService.findUpcomingReservations(limit);
        return ResponseEntity.ok(reservations);
    }


}
