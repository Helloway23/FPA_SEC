package com.HelloWay.HelloWay.ConrollersTest;

import com.HelloWay.HelloWay.Security.Jwt.JwtUtils;
import com.HelloWay.HelloWay.controllers.EventController;
import com.HelloWay.HelloWay.entities.Party;
import com.HelloWay.HelloWay.entities.Space;
import com.HelloWay.HelloWay.services.EventService;
import com.HelloWay.HelloWay.services.SpaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
public class EventControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private EventService eventService;

    @MockBean
    private SpaceService spaceService;

    @Autowired
    EventController eventController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup( eventController)
                .build();
    }

    @Test
    public void testCreateParty() throws Exception {
        // Mock the space
        Space space = new Space();
        space.setId_space(1L);

        // Create a party object
        Party party = new Party();
        party.setEventTitle("Birthday Party");
        party.setStartDate(LocalDateTime.of(2023, 7, 15, 10, 0));
        party.setEndDate(LocalDateTime.of(2023, 7, 15, 18, 0));
        party.setDescription("A fun birthday party");
        party.setNbParticipant(20);
        party.setPrice(100.0);
        party.setAllInclusive(true);
        party.setSpace(space);

        // Mock the service method
        when(spaceService.findSpaceById(1L)).thenReturn(space);
        when(eventService.createParty(any(Party.class))).thenReturn(party);

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/events/party/space/{spaceId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"eventTitle\":\"Birthday Party\",\"startDate\":\"2023-07-15T10:00:00\"," +
                                "\"endDate\":\"2023-07-15T18:00:00\",\"description\":\"A fun birthday party\"," +
                                "\"nbParticipant\":20,\"price\":100.0,\"allInclusive\":true}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventTitle").value("Birthday Party"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nbParticipant").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(100.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allInclusive").value(true))
                .andDo(print());

        // Verify that the service method was called
        verify(spaceService, times(1)).findSpaceById(1L);
        verify(eventService, times(1)).createParty(any(Party.class));
    }
}
