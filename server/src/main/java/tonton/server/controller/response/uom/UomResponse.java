package tonton.server.controller.response.uom;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UomResponse {
    private Long id;
    private String name;
    private String symbol;
}