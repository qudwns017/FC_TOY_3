package org.group6.travel.domain.s3.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.group6.travel.config.s3.S3Config;
import org.group6.travel.domain.s3.service.S3Service;
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


//    https://innovation123.tistory.com/197
//    https://gaeggu.tistory.com/33
    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<?> upload(@RequestPart(value = "image", required = false) MultipartFile image)
            throws IOException {
        String profileImage = s3Service.saveFile(image);
        return ResponseEntity.ok(profileImage);
    }
}
