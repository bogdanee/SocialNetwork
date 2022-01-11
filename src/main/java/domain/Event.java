package domain;

import java.time.LocalDateTime;
import java.util.List;

public class Event {
    private int id;
    private int organiser;
    private String name;
    private String description;
    private LocalDateTime date;
    private List<Integer> participants;
    private String imageURL;

    public Event(int id, int organiser, String name, String description, LocalDateTime date, List<Integer> participants, String imageURL) {
        this.id = id;
        this.organiser = organiser;
        this.name = name;
        this.description = description;
        this.date = date;
        this.participants = participants;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrganiser() {
        return organiser;
    }

    public void setOrganiser(int organiser) {
        this.organiser = organiser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Integer> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Integer> participants) {
        this.participants = participants;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", organiser=" + organiser +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", participants=" + participants +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
