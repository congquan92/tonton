package tonton.server.controller.response.upload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageUploadResponse {
    private String url;
    private String secureUrl;
    private String publicId;
    private String format;
    private Integer width;
    private Integer height;
    private Long bytes;
}
