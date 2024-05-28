package org.group6.travel.domain.reply.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reply")
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long replyId;

    //@ManyToOne
    //@JsonIgnore
    private Long userId; //private UserEntity user

    //@ManyToOne
    // @JsonIgnore
    private Long tripId; //private TripEntity trip

    @Column(name = "comment")
    private String replyComment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
