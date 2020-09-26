package microservices.book.cucumber.steps;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import microservices.book.cucumber.actors.Challenge;

import static org.assertj.core.api.Assertions.*;

public class ChallengeStepDefinitions {

    private Challenge userActor;

    @Given("a new user {word}")
    public void aNewUser(String user) {
        this.userActor = new Challenge(user);
    }

    @When("(s)he requests a new challenge")
    public void userRequestsANewChallenge() throws Exception {
        this.userActor.askForChallenge();
    }

    @Then("(s)he gets a mid-complexity multiplication to solve")
    public void getsAMidComplexityMultiplicationToSolve() {
        assertThat(this.userActor.getCurrentChallenge().getFactorA()).isBetween(9, 100);
        assertThat(this.userActor.getCurrentChallenge().getFactorB()).isBetween(9, 100);
    }

    @When("(s)he sends the {correct} challenge solution")
    public void userSendsTheCorrectChallengeSolution(boolean correct) throws Exception {
        this.userActor.solveChallenge(correct);
    }

    @Then("her/his stats include {int} {correct} attempt(s)")
    public void statsIncludeAttempts(int attemptNumber, boolean correct) throws Exception {
        var stats = this.userActor.retrieveStats();
        assertThat(stats).satisfies(s ->
                assertThat(s.stream().filter(r -> r.isCorrect() == correct).count()).isEqualTo(attemptNumber)
        );
    }

    @ParameterType("correct|incorrect")
    public boolean correct(String input) {
        return "correct".equalsIgnoreCase(input);
    }

}
