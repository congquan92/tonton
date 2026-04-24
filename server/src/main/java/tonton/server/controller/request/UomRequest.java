package tonton.server.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UomRequest {
    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 20)
    private String symbol;
}