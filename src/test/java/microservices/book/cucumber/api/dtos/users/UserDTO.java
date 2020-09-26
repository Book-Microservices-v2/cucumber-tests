package microservices.book.cucumber.api.dtos.users;

public class UserDTO {
    private long id;
    private String alias;

    public UserDTO() {
    }

    public long getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }
}
