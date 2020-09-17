package microservices.book;

import java.util.List;
import java.util.UUID;

import microservices.book.cucumber.dtos.challenge.AttemptRequestDTO;
import microservices.book.cucumber.dtos.challenge.AttemptResponseDTO;
import microservices.book.cucumber.dtos.challenge.ChallengeDTO;

import static org.assertj.core.api.Assertions.*;

public class User {

    private String userName;
    private String originalName;
    private long userId;
    private ChallengeDTO currentChallenge;
    private AttemptResponseDTO currentResponse;
    private APIClient apiClient;
    private List<AttemptResponseDTO> stats;

    public User(String userName) {
        this.userName = userName + "-" + UUID.randomUUID().toString();
        this.originalName = userName;
        this.apiClient = new APIClient();
    }

    public void askForChallenge() throws Exception {
        var challenge = apiClient.getChallenge();
        assertThat(challenge.statusCode()).isEqualTo(200);
        this.currentChallenge = apiClient.getChallenge().body();
    }

    public void solveChallenge(boolean correct) throws Exception {
        assertThat(this.currentChallenge)
                .as("You have to get a challenge first").isNotNull();
        var attemptResponse = apiClient.sendAttempt(
                AttemptRequestDTO.solve(this.currentChallenge, correct, this.userName)
        );
        assertThat(attemptResponse.statusCode()).isEqualTo(200);
        this.userId = attemptResponse.body().getUser().getId();
        this.currentResponse = attemptResponse.body();
    }

    public List<AttemptResponseDTO> retrieveStats() throws Exception {
        var stats = apiClient.getStats(this.userName);
        assertThat(stats.statusCode()).isEqualTo(200);
        this.stats = stats.body();
        return this.stats;
    }

    public ChallengeDTO getCurrentChallenge() {
        return currentChallenge;
    }

    public String getUserName() {
        return userName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public long getUserId() {
        return userId;
    }
}
