package tonton.server.controller.response.lead;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LeadResponse {
    private boolean success;
    private String message;
}
