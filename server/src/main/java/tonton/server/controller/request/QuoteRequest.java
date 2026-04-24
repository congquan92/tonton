package tonton.server.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tonton.server.common.enums.QuoteStatus;

@Getter
@Setter
public class QuoteRequest {
    @NotNull
    private Long userId;

    private QuoteStatus status;

    private String note;
}