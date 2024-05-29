package org.group6.travel.domain.trip.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.group6.travel.domain.trip.model.enums.DomesticType;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TripRequest {

    @NotBlank(message = "제목은 빈 값일 수 없습니다.")
    private String tripName;
    @NotNull(message = "시작일자는 빈 값일 수 없습니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;
    @NotNull(message = "종료일자는 빈 값일 수 없습니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;
    @NotNull(message = "국/내외 여부 항목은 빈 값일 수 없습니다.")
    private DomesticType domestic;
    @NotBlank
    private String tripComment;

    @AssertTrue(message = "종료일은 시작일을 앞설 수 없습니다.")
    public boolean isValidPeriod() {
        return endDate.isAfter(startDate) || startDate.isEqual(endDate);
    }
}
