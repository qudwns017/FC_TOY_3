package org.group6.travel.domain.reply.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.group6.travel.common.api.Api;
import org.group6.travel.common.error.ErrorCode;
import org.group6.travel.common.exception.ApiException;
import org.group6.travel.domain.reply.model.dto.ReplyDto;
import org.group6.travel.domain.reply.model.entity.ReplyEntity;
import org.group6.travel.domain.reply.model.request.ReplyRequest;
import org.group6.travel.domain.reply.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    public ReplyDto create(Long tripId, @Valid ReplyRequest replyRequest){
        //tripid없을 때, -> Errorcode 추가
        //login 하지 않았다면 아예 접근 불가하지 않나?
        var replyEntity = ReplyEntity.builder()
           .userId(tripId)
           .tripId(tripId)
           .comment(replyRequest.getComment())
           .createdAt(LocalDateTime.now())
           .build();

       try{
           replyRepository.save(replyEntity);
       }catch (Exception e){
           throw new ApiException(ErrorCode.NULL_POINT);
       }

       return ReplyDto.toDto(replyEntity);
    }
}
