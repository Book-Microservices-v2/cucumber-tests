package microservices.book.cucumber.actors;

import java.util.List;
import java.util.UUID;

import microservices.book.cucumber.api.APIClient;
import microservices.book.cucumber.api.dtos.challenge.AttemptRequestDTO;
import microservices.book.cucumber.api.dtos.challenge.AttemptResponseDTO;
import microservices.book.cucumber.api.dtos.challenge.ChallengeDTO;

import static org.assertj.core.api.Assertions.*;

public class Challenge {

    private final String userName;
    private final String originalName;
    private final APIClient apiClient;
    private long userId;
    private ChallengeDTO currentChallenge;

    public Challenge(String userName) {
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
                AttemptRequestDTO.solve(this.currentChallenge,
                        correct, this.userName)
        );
        assertThat(attemptResponse.statusCode()).isEqualTo(200);
        this.userId = attemptResponse.body().getUser().getId();
    }

    public List<AttemptResponseDTO> retrieveStats() throws Exception {
        var stats = apiClient.getStats(this.userName);
        assertThat(stats.statusCode()).isEqualTo(200);
        return stats.body();
    }

    public ChallengeDTO getCurrentChallenge() {
        return currentChallenge;
    }

    public String getOriginalName() {
        return originalName;
    }

    public long getUserId() {
        return userId;
    }
}
