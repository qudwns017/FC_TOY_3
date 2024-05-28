package org.group6.travel.domain.trip.service;

import lombok.RequiredArgsConstructor;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.trip.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;

    public List<TripEntity> getTripAll(){
        return tripRepository.findAll();
    }
    public TripEntity getTripById(Long tripId){
        return tripRepository.findById(tripId).get();
    }

    public List<TripEntity> getTripByKeyword(String keyword){
        return tripRepository.findByTripNameContains(keyword).get();
    }
}
