package microservices.book.cucumber.api.dtos.challenge;

public class ChallengeDTO {
    private final int factorA;
    private final int factorB;

    public ChallengeDTO() {
        this.factorA = -1;
        this.factorB = -1;
    }

    public int getFactorA() {
        return factorA;
    }

    public int getFactorB() {
        return factorB;
    }

    @Override
    public String toString() {
        return "ChallengeDTO{" +
                "factorA=" + factorA +
                ", factorB=" + factorB +
                '}';
    }
}
