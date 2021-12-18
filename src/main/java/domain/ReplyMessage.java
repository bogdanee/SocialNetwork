package domain;

import java.time.LocalDateTime;
import java.util.List;

public class ReplyMessage extends Message{
    private Message messageToReply;

    public ReplyMessage(int from, List<Integer> to, String message, LocalDateTime date, Message messageToReply) {
        super(from, to, message, date);
        this.messageToReply = messageToReply;
    }

    @Override
    public String toString() {
        return "ReplyMessage{" +
                "from=" + from +
                ", to=" + to +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", messageToReply=" + messageToReply +
                '}';
    }

    public Message getMessageToReply() {
        return messageToReply;
    }

    public void setMessageToReply(Message messageToReply) {
        this.messageToReply = messageToReply;
    }
}
