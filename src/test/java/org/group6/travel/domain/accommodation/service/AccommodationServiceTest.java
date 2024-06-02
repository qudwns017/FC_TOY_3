package org.group6.travel.domain.accommodation.service;

import org.group6.travel.domain.accommodation.model.dto.AccommodationDto;
import org.group6.travel.domain.accommodation.model.entity.AccommodationEntity;
import org.group6.travel.domain.accommodation.repository.AccommodationRepository;
import org.group6.travel.domain.reply.model.entity.ReplyEntity;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.model.enums.DomesticType;
import org.group6.travel.domain.trip.repository.TripRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class AccommodationServiceTest {

    @Mock
    private TripRepository tripRepository;
    @Mock
    private AccommodationRepository accommodationRepository;
    @InjectMocks
    private AccommodationService accommodationService;

    Long userId = 1L;
    Long tripId = 1L;
    Long accommodationId = 1L;
    String accommodationName = "accommodationName";

    private TripEntity createTripEntity(){
        return TripEntity.builder()
            .tripId(tripId).userId(userId)
            .tripName("tripname").startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(3))
            .likeCount(1)
            .domestic(DomesticType.DOMESTIC).tripComment("tripComment")
            .build();
    }

    private AccommodationEntity creatAccommodationEntity(TripEntity tripEntity){
        return AccommodationEntity.builder()
            .tripEntity(tripEntity)
            .id(accommodationId)
            .name(accommodationName)
            .checkInDatetime(LocalDateTime.now())
            .checkOutDatetime(LocalDateTime.now().plusDays(2))
            .latitude(234.342143)
            .longitude(323.2323)
            .build();
    }

    @BeforeAll
    static void setUp() {
    }

    @Test
    void compareUserIdTest() {
        TripEntity tripEntity = createTripEntity();
        boolean result = accommodationService.compareUserId(userId, tripEntity);
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("숙박 조회 테스트 ")
    void getAccommodationListTest() {
        TripEntity tripEntity = createTripEntity();
        AccommodationEntity accommodationEntity = creatAccommodationEntity(tripEntity);
        List<AccommodationEntity> accommodationEntityList = List.of(accommodationEntity);

        lenient().when(tripRepository.findByTripId(tripId)).thenReturn(Optional.of(tripEntity));
        lenient().when(accommodationRepository.findByTripEntity(tripEntity)).thenReturn(accommodationEntityList);

       List<AccommodationDto> result = accommodationService.getAccommodationList(tripId);

        assertNotNull(result);
        assertEquals(1, result.size());

    }

    @Test
    @DisplayName("숙박 생성")
    void createAccommodationTest() {
        TripEntity tripEntity = createTripEntity();
        AccommodationEntity accommodationEntity = creatAccommodationEntity(tripEntity);

        lenient().when(tripRepository.findByTripId(tripId)).thenReturn(Optional.of(tripEntity));
        lenient().when(accommodationRepository.save(accommodationEntity)).thenReturn(accommodationEntity);

        assertNotNull(accommodationService);
        assertEquals(accommodationId, accommodationEntity.getId());

    }

    @Test
    @DisplayName("숙박 삭제")
    void deleteAccommodationTest() {
        TripEntity tripEntity = createTripEntity();
        AccommodationEntity accommodationEntity = creatAccommodationEntity(tripEntity);

        lenient().when(tripRepository.findByTripId(tripId)).thenReturn(Optional.of(tripEntity));

        assertDoesNotThrow(()-> accommodationService.deleteAccommodation(tripId, userId,accommodationId));

        verify(accommodationRepository).deleteById(accommodationId);
    }

    @Test
    void isValidDateTime() {
        LocalDate startTravel = LocalDate.of(2024, 6, 1);
        LocalDate endTravel = LocalDate.of(2024, 6, 10);
        LocalDateTime checkIn = LocalDateTime.of(2024, 6, 2, 14, 0);
        LocalDateTime checkOut = LocalDateTime.of(2024, 6, 5, 12, 0);
        assertTrue(accommodationService.isValidDateTime(startTravel, endTravel, checkIn, checkOut));

        checkIn = LocalDateTime.of(2022, 5, 30, 14, 0);
        checkOut = LocalDateTime.of(2024, 6, 5, 12, 0);
        assertFalse(accommodationService.isValidDateTime(startTravel, endTravel, checkIn, checkOut));

    }
}