package org.group6.travel.domain.accommodation.repository;

import org.group6.travel.domain.accommodation.model.entity.AccommodationEntity;
import org.group6.travel.domain.accommodation.service.AccommodationService;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.model.enums.DomesticType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class AccommodationRepositoryTest {

    @Mock
    AccommodationRepository accommodationRepository;

    Long tripId = 1L;
    Long userId = 1L;
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


    @Test
    @DisplayName("여정아이디별 여정 찾기")
    void findByTripEntityTest() {
        TripEntity tripEntity = createTripEntity();
        AccommodationEntity accommodationEntity = creatAccommodationEntity(tripEntity);
        List<AccommodationEntity> accommodationEntityList = List.of(accommodationEntity);

        when(accommodationRepository.findByTripEntity(tripEntity)).thenReturn(List.of(accommodationEntity));

        List<AccommodationEntity> result = accommodationRepository.findByTripEntity(tripEntity);
        assertEquals(accommodationEntityList.size(), result.size());
    }
}