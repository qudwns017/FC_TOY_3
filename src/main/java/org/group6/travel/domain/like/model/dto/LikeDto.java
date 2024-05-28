package org.group6.travel.domain.like.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.group6.travel.domain.like.model.entity.LikeEntity;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LikeDto {

    private Long userId;
    private Long tripId;

    public static LikeDto toDto (LikeEntity likeEntity){
        return LikeDto.builder()
            .tripId(likeEntity.getTripId())
            .userId(likeEntity.getUserId())
            .build();
    }
}
