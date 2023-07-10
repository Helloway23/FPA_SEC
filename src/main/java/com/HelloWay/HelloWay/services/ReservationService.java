package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.entities.Board;
import com.HelloWay.HelloWay.entities.Reservation;
import com.HelloWay.HelloWay.entities.Space;
import com.HelloWay.HelloWay.entities.User;
import com.HelloWay.HelloWay.repos.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private static ReservationRepository reservationRepository;

    @Autowired
    private static SpaceService spaceService;

    @Autowired
    BoardService boardService;

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

    public List<Reservation> findReservationsBySpaceId(Long spaceId) {
        Space space = spaceService.findSpaceById(spaceId);
        if (space == null) {
            return new ArrayList<>();
        }
        return space.getReservations();
    }

    public List<Reservation> findReservationsByUserId(Long userId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            return new ArrayList<>();
        }
        return user.getReservations();
    }

    public List<Reservation> findReservationsByDateRange(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.findByStartDateBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
    }

    public List<Reservation> findUpcomingReservations(int limit) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return reservationRepository.findByStartDateAfterOrderByStartDateAsc(currentDateTime)
                .stream()
                .limit(limit)
                .collect(Collectors.toList());
    }


    public Reservation createReservationForSpaceAndUserWithBoard(Reservation reservation, Long spaceId, Long userId, Long boardId) {
        Space space = spaceService.findSpaceById(spaceId);
        Board board = boardService.findBoardById(boardId);
        User user = userService.findUserById(userId);
        reservation.setSpace(space);
        reservation.setUser(user);

        List<Board> boards = new ArrayList<>();
        boards = reservation.getBoards();
        boards.add(board);
        reservation.setBoards(boards);


        Reservation reservationObject = reservationRepository.save(reservation);

        List<Reservation> reservations = new ArrayList<>();
        reservations = space.getReservations();
        reservations.add(reservationObject);
        spaceService.updateSpace(space);

        List<Reservation> userReservations = new ArrayList<>();
        userReservations = user.getReservations();
        userReservations.add(reservationObject);
        userService.updateUser(user);

        //List<Reservation> boardReservations = new ArrayList<>();
        //TODO : we must update the relation between board and reservation
        //boardReservations = board.getReservation()

        board.setReservation(reservationObject);
        boardService.updateBoard(board);

        return reservationObject;
    }
}
