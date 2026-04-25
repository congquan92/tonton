package tonton.server.controller.response.quote;

import lombok.Builder;
import lombok.Getter;
import tonton.server.common.enums.QuoteStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class QuoteResponse {
    private Long id;
    private Long userId;
    private String userFullName;
    private QuoteStatus status;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}