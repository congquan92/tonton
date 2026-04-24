package tonton.server.exception;

import org.springframework.http.HttpStatus;
import tonton.server.common.enums.ErrorCode;

public class ForbiddenException extends ApiException {
    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN, message);
    }
}
