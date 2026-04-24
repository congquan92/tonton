package tonton.server.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import tonton.server.common.validation.PhoneNumber;

import java.time.LocalDate;

@Getter
@Setter
public class UserRequest {
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

    @NotNull
    private Long roleId;

    private Integer tokenVersion;
}