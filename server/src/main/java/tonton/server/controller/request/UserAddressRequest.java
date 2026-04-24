package tonton.server.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import tonton.server.common.validation.PhoneNumber;

@Getter
@Setter
public class UserAddressRequest {
    @NotNull
    private Long userId;

    @Size(max = 100)
    private String title;

    @NotBlank
    @Size(max = 100)
    private String receiverName;

    @NotBlank
    @Size(max = 20)
    @PhoneNumber
    private String receiverPhone;

    @NotBlank
    private String address;

    private Boolean isDefault;
}