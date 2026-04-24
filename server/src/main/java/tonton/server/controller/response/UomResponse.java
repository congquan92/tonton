package tonton.server.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UomResponse {
    private Long id;
    private String name;
    private String symbol;
}