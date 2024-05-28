package org.group6.travel.domain.trip.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.group6.travel.domain.trip.model.dto.TripRequest;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripService {
    private final TripRepository tripRepository;

    public TripEntity insertTrip(TripRequest tripRequest){
        return tripRepository.save(TripEntity.builder()
                        .userId((long)1)
                        .tripName(tripRequest.getTripName())
                        .startDate(tripRequest.getStartDate())
                        .endDate(tripRequest.getEndDate())
                        .domestic(tripRequest.getDomestic())
                        .likeCount(0)
                        .tripComment(tripRequest.getTripComment())
                .build());
    }

    public List<TripEntity> getTripAll(){
        return tripRepository.findAll();
    }
    public TripEntity getTripById(Long tripId){
        return tripRepository.findById(tripId).get();
    }

    public List<TripEntity> getTripByUserId(Long userId){
        return tripRepository.findByUserId(userId).get();
    }

    public List<TripEntity> getTripByKeyword(String keyword){
        return tripRepository.findByTripNameContains(keyword).get();
    }

    public TripEntity updateTrip(Long tripId, TripRequest tripRequest){
        Optional<TripEntity> entity = tripRepository.findById(tripId);
        if(entity.isPresent()){
            TripEntity saveEntity = entity.get();
            saveEntity.setTripName(tripRequest.getTripName());
            saveEntity.setStartDate(tripRequest.getStartDate());
            saveEntity.setEndDate(tripRequest.getEndDate());
            saveEntity.setDomestic(tripRequest.getDomestic());
            saveEntity.setTripComment(tripRequest.getTripComment());

            return tripRepository.save(saveEntity);
        }
        return null;
    }

    public void deleteTrip(Long tripId){
        Optional<TripEntity> entity = tripRepository.findById(tripId);
        if(entity.isPresent()) {
            tripRepository.delete(entity.get());
        }else{
           log.info("id 없음");
        }
    }
}
