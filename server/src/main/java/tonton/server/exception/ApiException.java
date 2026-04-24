package tonton.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import tonton.server.common.enums.ErrorCode;

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final ErrorCode errorCode;

    public ApiException(HttpStatus status, ErrorCode errorCode, String message) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }
}
