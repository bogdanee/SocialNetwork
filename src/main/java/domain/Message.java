package domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Message<ID> implements Serializable {
    @Serial
    private static final long serialVersionUID = 189237981273L;
    private ID id;
    protected int from;
    protected List<Integer> to;
    protected String message;
    protected LocalDateTime date;

    public Message(int from, List<Integer> to, String message, LocalDateTime date) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = date;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public List<Integer> getTo() {
        return to;
    }

    public void setTo(List<Integer> to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message<?> message1 = (Message<?>) o;
        return Objects.equals(id, message1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, message, date);
    }
}
