package domain;

import utils.Constants;

import java.time.LocalDateTime;
import java.util.Objects;

public class Friendship {
    private final int id1;
    private final int id2;
    private LocalDateTime date;

    public Friendship(int id1, int id2, LocalDateTime date) {
        this.id1 = id1;
        this.id2 = id2;
        this.date = date;
    }

    public int getId1() {
        return id1;
    }

    public int getId2() {
        return id2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Friendship)) return false;
        Friendship that = (Friendship) o;
        return getId1() == that.getId1() && getId2() == that.getId2();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId1(), getId2());
    }


    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id1=" + id1 +
                ", id2=" + id2 +
                ", date=" + date.format(Constants.DATE_TIME_FORMATTER) +
                '}';
    }
}
