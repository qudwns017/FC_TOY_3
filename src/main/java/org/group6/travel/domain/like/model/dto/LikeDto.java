package org.group6.travel.domain.like.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.group6.travel.domain.like.model.entity.LikeEntity;

@Data
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
