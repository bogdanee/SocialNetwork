package domain;

import java.time.LocalDateTime;
import java.util.List;

public class ReplyMessage extends Message{
    private int idMessageToReply;

    public ReplyMessage(int from, List<Integer> to, String message, LocalDateTime date, int idMessageToReply) {
        super(from, to, message, date);
        this.idMessageToReply = idMessageToReply;
    }

    @Override
    public String toString() {
        return "ReplyMessage{" +
                "from=" + from +
                ", to=" + to +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", messageToReply=" + idMessageToReply +
                '}';
    }

    public int getIdMessageToReply() {
        return idMessageToReply;
    }

    public void setIdMessageToReply(int idMessageToReply) {
        this.idMessageToReply = idMessageToReply;
    }
}
