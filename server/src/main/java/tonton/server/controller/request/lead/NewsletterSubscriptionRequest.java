package tonton.server.controller.request.lead;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsletterSubscriptionRequest {
    @Email
    @NotBlank
    private String email;
}
