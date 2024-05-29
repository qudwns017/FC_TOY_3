package org.group6.travel.domain.accommodation.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccommodationRequest {
    @NotBlank(message = "숙박 : 이름이 없습니다.")
    private String name;

    @NotNull(message = "숙박 : 체크인 시간이 없습니다.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime checkInDatetime;

    @NotNull(message = "숙박 : 체크아웃 시간이 없습니다.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime checkOutDatetime;

    @NotNull(message = "숙박 : latitude가 없습니다.")
    private Double lat;

    @NotNull(message = "숙박 : longitude가 없습니다.")
    private Double lng;

    @AssertTrue(message = "숙박 : 체크인/체크아웃 시간이 올바르지 않습니다.")
    public boolean isValidPeriod() {
        return checkOutDatetime.isAfter(checkInDatetime);
    }

}
