package validator;

import exception.ValidationException;

public interface Validator<E> {
    /**
     * validate an entity
     * @param entity - an Object
     * @throws ValidationException
     */
    void validate(E entity) throws ValidationException;
}
