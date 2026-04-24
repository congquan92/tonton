package tonton.server.exception;

import org.springframework.http.HttpStatus;
import tonton.server.common.enums.ErrorCode;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED, message);
    }
}
