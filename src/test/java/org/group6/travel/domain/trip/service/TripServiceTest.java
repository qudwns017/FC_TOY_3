package org.group6.travel.domain.trip.service;

import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.model.enums.DomesticType;
import org.group6.travel.domain.trip.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc

class TripServiceTest {

    @InjectMocks
    TripService tripService;

    @Mock
    TripRepository tripRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTrips() {
        //given
       /* Long tripId = 1L;
        Long userId = 1L;
        TripEntity tripEntity = TripEntity.builder()
            .tripId(tripId).userId(userId)
            .tripName("tripname").startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(10))
            .domestic(DomesticType.OVERSEAS).tripComment("TripComment")
            .build();
        List<TripEntity> trips = Arrays.asList(tripEntity);
*/

    }

    @Test
    void getTripsByKeyword() {
    }
}