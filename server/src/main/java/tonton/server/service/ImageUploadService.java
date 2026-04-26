package tonton.server.service;

import org.springframework.web.multipart.MultipartFile;
import tonton.server.controller.response.upload.ImageUploadResponse;

public interface ImageUploadService {
    ImageUploadResponse uploadImage(MultipartFile file, String folder);
}
