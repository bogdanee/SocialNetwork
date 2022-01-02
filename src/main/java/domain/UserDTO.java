package domain;

public class UserDTO {
    private int id;
    private String name;
    private String status;
    private String imageURL;


    public UserDTO(int id, String name, String status, String imageURL) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
