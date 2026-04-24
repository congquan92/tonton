package tonton.server.exception;

import org.springframework.http.HttpStatus;
import tonton.server.common.enums.ErrorCode;

public class NotFoundException extends ApiException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, message);
    }
}
