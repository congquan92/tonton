package tonton.server.exception;

import org.springframework.http.HttpStatus;
import tonton.server.common.enums.ErrorCode;

public class ConflictException extends ApiException {
    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, ErrorCode.CONFLICT, message);
    }
}
