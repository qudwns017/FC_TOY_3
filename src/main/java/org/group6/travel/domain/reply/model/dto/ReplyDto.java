package org.group6.travel.domain.reply.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.group6.travel.domain.reply.model.entity.ReplyEntity;

import java.time.LocalDateTime;

@Data
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
            .id(replyEntity.getReplyId())
            .tripId(replyEntity.getTripId())
            .userId(replyEntity.getUserId())
            .comment(replyEntity.getReplyComment())
            .createdAt(replyEntity.getCreatedAt())
            .updatedAt(replyEntity.getUpdatedAt())
            .build();
    }



}
