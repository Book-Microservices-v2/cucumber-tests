package microservices.book.cucumber.actors;

import java.util.List;
import java.util.Optional;

import microservices.book.cucumber.api.APIClient;
import microservices.book.cucumber.api.dtos.leaderboard.LeaderboardRowDTO;

import static org.assertj.core.api.Assertions.*;

public class Leaderboard {

    private final APIClient apiClient;
    private List<LeaderboardRowDTO> leaderboard;

    public Leaderboard() {
        apiClient = new APIClient();
    }

    public Leaderboard update() throws Exception {
        var leaderboard = apiClient.getLeaderboard();
        assertThat(leaderboard.statusCode()).isEqualTo(200);
        this.leaderboard = leaderboard.body();
        return this;
    }

    public int whatPosition(long userId) {
        for (int i = 0; i < leaderboard.size(); i++) {
            if (leaderboard.get(i).getUserId() == userId) {
                return i + 1;
            }
        }
        return -1;
    }

    public Optional<LeaderboardRowDTO> getByUserId(long userId) {
        return leaderboard.stream()
                .filter(row -> row.getUserId() == userId).findAny();
    }
}
