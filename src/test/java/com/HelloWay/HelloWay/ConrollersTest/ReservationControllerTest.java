package com.HelloWay.HelloWay.ConrollersTest;

import com.HelloWay.HelloWay.Security.Jwt.JwtUtils;
import com.HelloWay.HelloWay.controllers.ReservationController;
import com.HelloWay.HelloWay.entities.Board;
import com.HelloWay.HelloWay.entities.Reservation;
import com.HelloWay.HelloWay.services.NotificationService;
import com.HelloWay.HelloWay.services.ReservationService;
import com.HelloWay.HelloWay.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
public class ReservationControllerTest {


    private MockMvc mockMvc;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private UserService userService;

    @Autowired
    ReservationController reservationController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController)
                .build();
    }

    @Test
    public void testGetAllReservations() throws Exception {
        // Mock reservations
        List<Reservation> reservations;
        reservations = new ArrayList<>();
        reservations.add(new Reservation());
        reservations.add(new Reservation());

        // Mock the service method
        when(reservationService.findAllReservations()).thenReturn(reservations);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(reservations.size()))
                .andDo(print());

        // Verify that the service method was called
        verify(reservationService, times(1)).findAllReservations();
    }


    @Test
    public void testGetReservationById_ExistingId() throws Exception {
        // Mock reservation
        Reservation reservation = new Reservation();
        reservation.setIdReservation(1L);

        // Mock the service method
        when(reservationService.findReservationById(1L)).thenReturn(reservation);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idReservation").value(reservation.getIdReservation()))
                .andDo(print());

        // Verify that the service method was called with the correct ID
        verify(reservationService, times(1)).findReservationById(1L);
    }

    @Test
    public void testGetReservationById_NonExistentId() throws Exception {
        // Mock the service method to return null for a non-existent ID
        when(reservationService.findReservationById(2L)).thenReturn(null);

        // Perform the GET request for a non-existent ID
        String expectedContent = "{\"message\": \"Reservation not found\"}";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations/{id}", 2L))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(expectedContent.trim()))
                .andDo(print());

        // Verify that the service method was called with the correct ID
        verify(reservationService, times(1)).findReservationById(2L);
    }

    /*
    @Test
    public void testGetTablesByIdReservation_ValidId() throws Exception {
        // Create a reservation with a valid ID
        Long validReservationId = 1L;

        // Mock the reservation and boards
        Reservation reservation = Mockito.mock(Reservation.class);
        when(reservation.getIdReservation()).thenReturn(validReservationId);
        List<Board> boards = Collections.singletonList(new Board()); // Use Mockito.mock to create a mock Board
        when(reservation.getBoards()).thenReturn(boards);

        // Mock the reservationService to return the reservation
        when(reservationService.findReservationById(validReservationId)).thenReturn(reservation);

        // Perform the GET request
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/tables/{id}", validReservationId));

        // Verify that the response status is OK and content type is JSON
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        // Verify that the response content contains the boards (in this case, a single board)
        String expectedContent = "[{\"boardId\":null,\"boardName\":null,\"capacity\":0}]";
        resultActions.andExpect(MockMvcResultMatchers.content().json(expectedContent, true)); // Use the lenient check

        // Verify that the service method was called with the correct ID
        verify(reservationService, times(1)).findReservationById(validReservationId);
    }





    @Test
    public void testGetTablesByIdReservation_NonExistentId() throws Exception {
        // Mock the reservationService to return null for a non-existent ID
        Long nonExistentReservationId = 2L;
        when(reservationService.findReservationById(nonExistentReservationId)).thenReturn(null);

        // Set up the MockMvc instance
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

        // Perform the GET request with the non-existent ID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tables/{id}", nonExistentReservationId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("")); // No content in the response

        // Verify that the service method was called with the correct ID
        verify(reservationService, times(1)).findReservationById(nonExistentReservationId);
    }

    
     */

}
