package pet.hub.common.exception;

import pet.hub.common.advice.ErrorCode;

public class EntityNotFoundException extends BusinessException{

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
