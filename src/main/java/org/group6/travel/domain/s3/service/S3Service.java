package org.group6.travel.domain.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.group6.travel.config.s3.S3Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, originalFilename).toString();
    }
//    public String upload(MultipartFile image) {
//        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
//            throw new AmazonS3Exception("비어 있는 이미지 파일");
//        }
//        S3ConnectionTest();
//        return uploadImage(image);
//    }
//
//    public void S3ConnectionTest() {
//        System.out.println("Listing S3 buckets:");
//        amazonS3.listBuckets().forEach(bucket -> System.out.println(bucket.getName()));
//    }
//
//    private String uploadImage(MultipartFile image) {
//        validateImageFilenameExtension(image.getOriginalFilename());
//        try {
//            return uploadImageToS3(image);
//        } catch (IOException e) {
//            throw new AmazonS3Exception("이미지 업로드 입출력 예외 발생");
//        }
//    }
//
//    private void validateImageFilenameExtension(String imageFilename) {
//        int extensionDotIndex = imageFilename.lastIndexOf(".");
//        if (extensionDotIndex == -1) {
//            throw new AmazonS3Exception("확장자가 존재하지 않습니다.");
//        }
//
//        String extension = imageFilename.substring(extensionDotIndex + 1).toLowerCase();
//
//        List<String> allowedExtensionList = Arrays.asList("jpg", "jpeg", "png", "gif");
//        if (!allowedExtensionList.contains(extension)) {
//            throw new AmazonS3Exception("올바르지 않은 확장자");
//        }
//    }
//
//    private String uploadImageToS3(MultipartFile image) throws IOException {
//        String originalFilename = image.getOriginalFilename();
//        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//
//        String s3Filename = UUID.randomUUID().toString().substring(0, 8) + originalFilename;
//
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentType("image/" + extension);
//        metadata.setContentLength(image.getSize());
//
//        try (InputStream inputStream = image.getInputStream()) {
//            PutObjectRequest putObjectRequest =
//                    new PutObjectRequest(bucketName, s3Filename, inputStream, metadata)
//                            .withCannedAcl(CannedAccessControlList.PublicRead);
//            amazonS3.putObject(putObjectRequest); // put image to S3
//        } catch (Exception e) {
//            throw new AmazonS3Exception("putObject Exception");
//        }
//
//        return amazonS3.getUrl(bucketName, s3Filename).toString();
//    }
}
