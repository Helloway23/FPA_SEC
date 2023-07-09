package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.entities.Reservation;
import com.HelloWay.HelloWay.entities.Space;
import com.HelloWay.HelloWay.entities.User;
import com.HelloWay.HelloWay.repos.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private static ReservationRepository reservationRepository;

    @Autowired
    private static SpaceService spaceService;

    @Autowired
    private static UserService userService;



    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElse(null);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public Reservation createReservationForSpaceAndUser(Reservation reservation,
                                                        Long spaceId,
                                                        Long userId){
        Space space = spaceService.findSpaceById(spaceId);
        User user = userService.findUserById(userId);
        reservation.setSpace(space);
        reservation.setUser(user);
        Reservation reservationObject = reservationRepository.save(reservation);

        List<Reservation> reservations = new ArrayList<>();
        reservations = space.getReservations();
        reservations.add(reservationObject);
        spaceService.updateSpace(space);

        List<Reservation> userReservations = new ArrayList<>();
        userReservations = user.getReservations();
        userReservations.add(reservationObject);
        userService.updateUser(user);



        return reservationObject;
    }


}
