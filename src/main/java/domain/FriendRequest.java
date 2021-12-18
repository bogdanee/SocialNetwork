package domain;

import utils.Constants;
import utils.RequestStatus;

import java.time.LocalDateTime;

public class FriendRequest extends Friendship{
    private RequestStatus status;

    public FriendRequest(int id1, int id2, LocalDateTime date) {
        super(id1, id2, date);
        status = RequestStatus.PENDING;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return  "Sent from user with id " + super.getId1()+
                ", on " + super.getDate().format(Constants.DATE_TIME_FORMATTER);
    }
}
