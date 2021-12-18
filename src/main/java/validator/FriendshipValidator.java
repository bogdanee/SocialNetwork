package validator;

import exception.ValidationException;
import domain.Friendship;

public class FriendshipValidator implements Validator<Friendship> {
    /**
     * Validate a friendship
     * @param friendship - Friendship
     * @throws ValidationException if friendship's attributes are not valid
     */
    @Override
    public void validate(Friendship friendship) throws ValidationException {
        if (friendship.getId1() < 0 || friendship.getId2() < 0)
            throw new ValidationException("id invalid!\n");
    }
}
