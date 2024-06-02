package org.group6.travel.domain.trip.repository;

import org.assertj.core.api.Assertions;
import org.group6.travel.domain.trip.model.dto.TripRequest;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.model.enums.DomesticType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TripRepositoryTest {
    @Autowired
    private TripRepository tripRepository;

    Long userId = 1L;

    private TripEntity createTripEntity(){
        return TripEntity.builder()
                //.tripId(tripId)
                .userId(userId)
                .tripName("tripname").startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .likeCount(0)
                .domestic(DomesticType.DOMESTIC)
                .tripComment("tripComment")
                .build();
    }

    @Test
    @DisplayName("여행 DB 저장")
    void createTrip(){
        TripEntity tripEntity = createTripEntity();
        TripEntity saveEntity = tripRepository.save(tripEntity);

        Assertions.assertThat(tripEntity).isSameAs(saveEntity);
        Assertions.assertThat(tripEntity.getTripId()).isEqualTo(saveEntity.getTripId());
    }

    @Test
    @DisplayName("여행 조회 확인")
    void findTripAll(){
        TripEntity saveEntity = tripRepository.save(createTripEntity());
        TripEntity saveEntity2 = tripRepository.save(createTripEntity());

        List<TripEntity> findEntities = tripRepository.findAll();

        Assertions.assertThat(findEntities.get(0).getTripId()).isEqualTo(saveEntity.getTripId());
        Assertions.assertThat(findEntities.get(1).getTripId()).isEqualTo(saveEntity2.getTripId());
    }


}
