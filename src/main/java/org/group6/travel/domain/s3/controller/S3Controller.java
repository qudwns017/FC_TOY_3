package org.group6.travel.domain.s3.controller;

import lombok.RequiredArgsConstructor;
import org.group6.travel.config.s3.S3Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class S3Controller {
    private final S3Config s3Config;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

//    https://innovation123.tistory.com/197
//    https://gaeggu.tistory.com/33

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestPart(value = "image", required = false) MultipartFile image) {
        String profileImage = null;
        return null;
    }
}
