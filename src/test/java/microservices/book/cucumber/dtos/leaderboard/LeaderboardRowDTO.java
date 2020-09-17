package microservices.book.cucumber.dtos.leaderboard;

import java.util.List;

public class LeaderboardRowDTO {

    private long userId;
    private long totalScore;
    private List<String> badges;

    public LeaderboardRowDTO() {
    }

    public long getUserId() {
        return userId;
    }

    public long getTotalScore() {
        return totalScore;
    }

    public List<String> getBadges() {
        return badges;
    }
}
