package microservices.book.cucumber.steps;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import microservices.book.Leaderboard;
import microservices.book.User;
import microservices.book.cucumber.dtos.leaderboard.LeaderboardRowDTO;

import static org.assertj.core.api.Assertions.*;
import static org.awaitility.Awaitility.*;

public class GameStepDefinitions {

    private Map<String, User> userActors;
    private final Leaderboard leaderboardActor;

    public GameStepDefinitions() {
        this.leaderboardActor = new Leaderboard();
    }

    @Given("the following solved challenges")
    public void theFollowingSolvedChallenges(DataTable dataTable) throws Exception {
        processSolvedChallenges(dataTable);
    }

    private void processSolvedChallenges(DataTable userToSolvedChallenges) throws Exception {
        userActors = new HashMap<>();
        for (var userToSolved : userToSolvedChallenges.asMaps()) {
            var user = new User(userToSolved.get("user"));
            user.askForChallenge();
            int solved = Integer.parseInt(userToSolved.get("solved_challenges"));
            for (int i = 0; i < solved; i++) {
                user.solveChallenge(true);
            }
            userActors.put(user.getOriginalName(), user);
        }
    }

    @Then("{word} has {int} points")
    public void userHasPoints(String user, long score) {
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(
                () -> {
                    Optional<LeaderboardRowDTO> optionalRow = this.leaderboardActor
                            .update()
                            .getByUserId(userActors.get(user).getUserId());
                    assertThat(optionalRow).isPresent()
                            .map(LeaderboardRowDTO::getTotalScore).hasValue(score);
                }
        );
    }

    @Then("{word} has the {string} badge")
    public void userHasBadge(String user, String badge) {
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(
                () -> {
                    Optional<LeaderboardRowDTO> optionalRow = this.leaderboardActor
                            .update()
                            .getByUserId(userActors.get(user).getUserId());
                    assertThat(optionalRow).isPresent();
                    assertThat(optionalRow.get().getBadges()).contains(badge);
                }
        );
    }

    @Then("{word} is above {word} in the ranking")
    public void userIsAboveUser(String userAbove, String userBelow) throws Exception {
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(
                () -> {
                    var updatedLeaderboard = this.leaderboardActor.update();
                    int positionAbove = updatedLeaderboard.whatPosition(
                            userActors.get(userAbove).getUserId()
                    );
                    int positionBelow = updatedLeaderboard.whatPosition(
                            userActors.get(userBelow).getUserId()
                    );
                    assertThat(positionAbove).isLessThan(positionBelow);
                }
        );
    }

}
