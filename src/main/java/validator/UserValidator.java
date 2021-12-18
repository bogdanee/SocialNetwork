package validator;

import exception.ValidationException;
import domain.User;

import static utils.Constants.*;

public class UserValidator implements Validator<User>{
    /**
     * Validate a user
     * @param user - User
     * @throws ValidationException if user's attributes are not valid
     */
    @Override
    public void validate(User user) throws ValidationException {
        String errorMessage = "";
        if (!user.getFirstName().matches("[a-zA-Z -]+"))
            errorMessage += ERROR_CODE_INVALID_FN;
        if (!user.getLastName().matches("[a-zA-Z -]+"))
            errorMessage += ERROR_CODE_INVALID_LN;
        if (user.getUsername().length()<8)
            errorMessage += ERROR_CODE_INVALID_UN;
        if (user.getPassword().length()<8)
            errorMessage += ERROR_CODE_INVALID_P;
        if (!errorMessage.isEmpty())
            throw new ValidationException(errorMessage);
    }
}
