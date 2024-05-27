package org.group6.travel.domain.reply.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.group6.travel.domain.reply.model.entity.ReplyEntity;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReplyDto {

    private Long id;

    private Long tripId;

    private Long userId;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static ReplyDto toDto(ReplyEntity replyEntity){
        return  ReplyDto.builder()
            .id(replyEntity.getId())
            .tripId(replyEntity.getTripId())
            .userId(replyEntity.getUserId())
            .comment(replyEntity.getComment())
            .createdAt(replyEntity.getCreatedAt())
            .updatedAt(replyEntity.getUpdatedAt())
            .build();
    }

}
