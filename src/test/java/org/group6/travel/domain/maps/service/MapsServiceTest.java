package org.group6.travel.domain.maps.service;

import static org.junit.jupiter.api.Assertions.*;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class MapsServiceTest {

    @Autowired
    private GeoApiContext geoApiContext;

    @DisplayName("getLatLngFromAddress : 구글 API를 통해 주소를 보내면 위도와 경도를 반환한다.")
    @Test
    void 주소로_위도경도_받아오기() throws Exception {

        final LatLng wants = new LatLng(37.57702070,126.97663100);

        // given
        final String address = "서울특별시 종로구 효자로 12";
        GeocodingApiRequest request = GeocodingApi.geocode(geoApiContext, address);

        // when
        GeocodingResult[] res = request.await();
        var locationResult = res[0].geometry.location;

        // then
        // Assertions.assertThat(locationResult).isEqualTo(wants);
        System.out.println(locationResult);
        assertEquals(wants, locationResult);

    }
}