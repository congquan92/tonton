package tonton.server.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAddressResponse {
    private Long id;
    private Long userId;
    private String title;
    private String receiverName;
    private String receiverPhone;
    private String address;
    private Boolean isDefault;
}