package pet.hub.common.exception;

import pet.hub.common.advice.ErrorCode;

public class InvalidValueException extends BusinessException{

    public InvalidValueException(String value) {
        super(value, ErrorCode.INVALID_INPUT_VALUE);
    }
}
