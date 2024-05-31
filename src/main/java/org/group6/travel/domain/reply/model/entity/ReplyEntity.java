package org.group6.travel.domain.reply.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.*;
import org.group6.travel.common.model.BaseEntity;
import org.group6.travel.domain.trip.model.entity.TripEntity;
import org.group6.travel.domain.user.model.entity.UserEntity;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reply")
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReplyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long replyId;

    //@ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id")
    private Long userId; //private UserEntity user

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="trip_id")
    @ToString.Exclude
    private TripEntity tripEntity;

    @Column(name = "comment")
    private String replyComment;

    /*private LocalDateTime createdAt;

    private LocalDateTime updatedAt;*/

}
