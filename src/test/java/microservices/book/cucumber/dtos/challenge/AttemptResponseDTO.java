package microservices.book.cucumber.dtos.challenge;

import microservices.book.cucumber.dtos.users.UserDTO;

public class AttemptResponseDTO {

    private int resultAttempt;
    private boolean correct;
    private UserDTO user;

    public AttemptResponseDTO() {
    }

    public int getResultAttempt() {
        return resultAttempt;
    }

    public boolean isCorrect() {
        return correct;
    }

    public UserDTO getUser() {
        return user;
    }

}
