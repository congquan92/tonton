package tonton.server.controller.request.lead;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import tonton.server.common.validation.PhoneNumber;

@Getter
@Setter
public class ContactMessageRequest {
    @NotBlank
    @Size(max = 150)
    private String fullName;

    @Size(max = 20)
    @PhoneNumber
    private String phoneNumber;

    @Email
    private String email;

    @Size(max = 100)
    private String sourcePage;

    @NotBlank
    private String message;
}
