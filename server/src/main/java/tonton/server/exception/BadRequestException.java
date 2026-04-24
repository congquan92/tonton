package tonton.server.exception;

import org.springframework.http.HttpStatus;
import tonton.server.common.enums.ErrorCode;

public class BadRequestException extends ApiException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, message);
    }
}
