package org.group6.travel.domain.itinerary.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group6.travel.domain.itinerary.model.enums.ItineraryType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryRequest {

    @NotBlank(message= "여정 이름을 입력해주세요.")
    @Size(min = 1, max = 50, message = "제목은 1 ~ 50자 사이여야 합니다.")
    private String itineraryName;

    @NotNull
    private ItineraryType type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @NotNull(message = "시작일자는 빈 값일 수 없습니다.")
    private LocalDateTime startDatetime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @NotNull(message= "도착시간은 빈칸일 수 없습니다.")
    private LocalDateTime endDatetime;

    private String itineraryComment;

    private String transportation;
    private String departurePlace;
    private String arrivalPlace;
    private Double departureLatitude;
    private Double departureLongitude;
    private Double arrivalLatitude;
    private Double arrivalLongitude;
    private String place;
    private Double latitude;
    private Double longitude;

    @AssertTrue(message = "종료시간은 시각시간을 앞설 수 없습니다.")
    public boolean isValidPeriod() {
        return endDatetime.isAfter(startDatetime);
    }

}
