package tonton.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tonton.server.controller.request.lead.ContactMessageRequest;
import tonton.server.controller.request.lead.NewsletterSubscriptionRequest;
import tonton.server.controller.response.lead.LeadResponse;
import tonton.server.model.ContactMessage;
import tonton.server.model.NewsletterSubscription;
import tonton.server.repository.ContactMessageRepository;
import tonton.server.repository.NewsletterSubscriptionRepository;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicLeadController {

    private final NewsletterSubscriptionRepository newsletterSubscriptionRepository;
    private final ContactMessageRepository contactMessageRepository;

    @PostMapping("/newsletter-subscriptions")
    public ResponseEntity<LeadResponse> subscribeNewsletter(@Valid @RequestBody NewsletterSubscriptionRequest request) {
        NewsletterSubscription subscription = newsletterSubscriptionRepository.findByEmail(request.getEmail())
                .orElseGet(NewsletterSubscription::new);
        subscription.setEmail(request.getEmail().trim().toLowerCase());
        subscription.setIsActive(true);
        newsletterSubscriptionRepository.save(subscription);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                LeadResponse.builder()
                        .success(true)
                        .message("Đăng ký nhận tin thành công")
                        .build()
        );
    }

    @PostMapping("/contact-messages")
    public ResponseEntity<LeadResponse> createContactMessage(@Valid @RequestBody ContactMessageRequest request) {
        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setFullName(request.getFullName());
        contactMessage.setPhoneNumber(request.getPhoneNumber());
        contactMessage.setEmail(request.getEmail());
        contactMessage.setSourcePage(request.getSourcePage());
        contactMessage.setMessage(request.getMessage());
        contactMessageRepository.save(contactMessage);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                LeadResponse.builder()
                        .success(true)
                        .message("Gửi liên hệ thành công, chúng tôi sẽ phản hồi sớm")
                        .build()
        );
    }
}
