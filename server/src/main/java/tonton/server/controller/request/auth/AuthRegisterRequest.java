package tonton.server.controller.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import tonton.server.common.validation.PhoneNumber;

import java.time.LocalDate;

@Getter
@Setter
public class AuthRegisterRequest {
    @NotBlank
    @Size(max = 20)
    @PhoneNumber
    private String phoneNumber;

    @NotBlank
    @Size(max = 100)
    private String fullName;

    private LocalDate dateOfBirth;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6, max = 255)
    private String password;
}
