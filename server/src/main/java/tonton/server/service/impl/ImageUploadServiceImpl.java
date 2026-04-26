package tonton.server.service.impl;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tonton.server.common.enums.ErrorCode;
import tonton.server.config.cloudinary.CloudinaryProperties;
import tonton.server.controller.response.upload.ImageUploadResponse;
import tonton.server.exception.ApiException;
import tonton.server.exception.BadRequestException;
import tonton.server.service.ImageUploadService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageUploadServiceImpl implements ImageUploadService {

    private final Cloudinary cloudinary;
    private final CloudinaryProperties cloudinaryProperties;

    @Override
    public ImageUploadResponse uploadImage(MultipartFile file, String folder) {
        validateCloudinaryConfig();
        validateFile(file);

        Map<String, Object> options = new HashMap<>();
        options.put("resource_type", "image");

        String targetFolder = StringUtils.hasText(folder)
                ? folder.trim()
                : cloudinaryProperties.getDefaultFolder();
        if (StringUtils.hasText(targetFolder)) {
            options.put("folder", targetFolder.trim());
        }

        Map<?, ?> uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        } catch (IOException ex) {
            throw new ApiException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_SERVER_ERROR,
                    "Upload image to Cloudinary failed"
            );
        }

        String url = valueAsString(uploadResult.get("url"));
        String secureUrl = valueAsString(uploadResult.get("secure_url"));
        String publicId = valueAsString(uploadResult.get("public_id"));

        if (!StringUtils.hasText(secureUrl) && !StringUtils.hasText(url)) {
            throw new ApiException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_SERVER_ERROR,
                    "Cloudinary did not return image URL"
            );
        }

        return ImageUploadResponse.builder()
                .url(url)
                .secureUrl(secureUrl)
                .publicId(publicId)
                .format(valueAsString(uploadResult.get("format")))
                .width(valueAsInteger(uploadResult.get("width")))
                .height(valueAsInteger(uploadResult.get("height")))
                .bytes(valueAsLong(uploadResult.get("bytes")))
                .build();
    }

    private void validateCloudinaryConfig() {
        if (!StringUtils.hasText(cloudinaryProperties.getCloudName())
                || !StringUtils.hasText(cloudinaryProperties.getApiKey())
                || !StringUtils.hasText(cloudinaryProperties.getApiSecret())) {
            throw new ApiException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_SERVER_ERROR,
                    "Missing Cloudinary config: cloud-name, api-key, api-secret"
            );
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Image file is invalid or empty");
        }

        String contentType = file.getContentType();
        if (!StringUtils.hasText(contentType) || !contentType.startsWith("image/")) {
            throw new BadRequestException("Only image files are accepted");
        }
    }

    private String valueAsString(Object value) {
        return value == null ? null : value.toString();
    }

    private Integer valueAsInteger(Object value) {
        if (value instanceof Number number) {
            return number.intValue();
        }
        return null;
    }

    private Long valueAsLong(Object value) {
        if (value instanceof Number number) {
            return number.longValue();
        }
        return null;
    }
}
