package microservices.book.cucumber.api.dtos.challenge;

import java.util.concurrent.ThreadLocalRandom;

public class AttemptRequestDTO {

    private int factorA, factorB;
    private String userAlias;
    private int guess;

    private AttemptRequestDTO(int factorA, int factorB, String userAlias, int guess) {
        this.factorA = factorA;
        this.factorB = factorB;
        this.userAlias = userAlias;
        this.guess = guess;
    }

    public static AttemptRequestDTO solve(ChallengeDTO challenge, boolean correct, String user) {
        return new AttemptRequestDTO(challenge.getFactorA(),
                challenge.getFactorB(),
                user,
                correct ? challenge.getFactorA() * challenge.getFactorB() :
                        ThreadLocalRandom.current().nextInt(100, 10000));
    }

    public int getFactorA() {
        return factorA;
    }

    public int getFactorB() {
        return factorB;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public int getGuess() {
        return guess;
    }
}
