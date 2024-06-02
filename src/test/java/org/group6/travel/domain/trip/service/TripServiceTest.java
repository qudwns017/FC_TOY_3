package org.group6.travel.domain.trip.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.group6.travel.domain.like.repository.LikeRepository;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.model.enums.DomesticType;
import org.group6.travel.domain.trip.repository.TripRepository;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TripServiceTest {
    @Autowired
    private TripService tripService;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeRepository likeRepository;

    UserEntity user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(createUser("test"));
    }

    @Test
    @DisplayName("로그인_유저아이디와_여행_작성자_유저아이디_비교")
    void compareUserIdTest() {
        TripEntity trip = createTrip(user.getUserId());

        TripEntity savedTrip = tripRepository.save(trip);
        UserEntity savedUser = userRepository.save(user);

        assertThat(savedTrip.getUserId()).isEqualTo(savedUser.getUserId());
    }

    @Test
    @DisplayName("여행_추가_후_사이즈_비교")
    void getTripsTest() {
        List<TripEntity> beforeTripEntityList = tripRepository.findAll();
        tripRepository.saveAll(Arrays.asList(
                createTrip(user.getUserId()),
                createTrip(user.getUserId())
        ));

        List<TripEntity> afterTripEntityList = tripRepository.findAll();

        assertEquals(beforeTripEntityList.size() + 2, afterTripEntityList.size());
    }

    @Test
    @DisplayName("여행_아이디_기반_여행_불러오기")
    void getTripsByUserTest() {
        tripRepository.saveAll(Arrays.asList(
                createTrip(user.getUserId()),
                createTrip(user.getUserId())
        ));

        Optional<List<TripEntity>> tripByUserOptionalList = tripRepository.findByUserId(user.getUserId());

        List<TripEntity> tripList = tripByUserOptionalList.orElseGet(ArrayList::new);

        assertEquals(2, tripList.size());

    }

    private TripEntity createTrip(Long userId) {
        return TripEntity.builder()
                .userId(userId)
                .tripName("tripName")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .domestic(DomesticType.DOMESTIC)
                .likeCount(0)
                .tripComment("comment")
                .build();
    }

    private UserEntity createUser(String userName) {
        return UserEntity.builder()
                .email("test@test.com")
                .userName(userName)
                .encryptedPassword("test")
                .build();
    }
}
