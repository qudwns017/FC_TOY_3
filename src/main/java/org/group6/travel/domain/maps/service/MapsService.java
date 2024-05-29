package org.group6.travel.domain.maps.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MapsService {

    private final GeoApiContext geoApiContext;

    public LatLng getLatLngFromAddress(String address) {
        GeocodingApiRequest request = GeocodingApi.geocode(geoApiContext, address);
        try {
            GeocodingResult[] results = request.await();

            if (results != null && results.length > 0) {
                return results[0].geometry.location;
            } else {
                throw new ApiException(ErrorCode.LOCATION_NOT_EXIST);
            }

        } catch (Exception e) {
            log.error("지도 서비스 에러 : {}", e.toString());
            throw new ApiException(ErrorCode.SERVER_ERROR);
        }
    }
}
