package microservices.book.cucumber.api;

import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import microservices.book.cucumber.api.dtos.challenge.AttemptRequestDTO;
import microservices.book.cucumber.api.dtos.challenge.AttemptResponseDTO;
import microservices.book.cucumber.api.dtos.challenge.ChallengeDTO;
import microservices.book.cucumber.api.dtos.leaderboard.LeaderboardRowDTO;

import static java.net.http.HttpRequest.BodyPublishers.*;

public class APIClient {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final String BACKEND_HOST = "http://localhost:8000";

    private final HttpClient httpClient;

    public APIClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public HttpResponse<ChallengeDTO> getChallenge() throws Exception {
        var getRandom = HttpRequest.newBuilder(
                URI.create(BACKEND_HOST + "/challenges/random")
        ).GET().build();
        return httpClient.send(getRandom, new JsonBodyHandler<>(ChallengeDTO.class));
    }

    public HttpResponse<AttemptResponseDTO> sendAttempt(AttemptRequestDTO attempt) throws Exception {
        var sendChallenge = HttpRequest.newBuilder(
                URI.create(BACKEND_HOST + "/attempts"))
                .header("Content-Type", "application/json")
                .POST(ofString(OBJECT_MAPPER.writeValueAsString(attempt))).build();
        return httpClient.send(sendChallenge, new JsonBodyHandler<>(AttemptResponseDTO.class));
    }

    public HttpResponse<List<AttemptResponseDTO>> getStats(String user) throws Exception {
        var getStats = HttpRequest.newBuilder(
                URI.create(BACKEND_HOST + "/attempts?alias=" + user)
        ).GET().build();
        return httpClient.send(getStats, new JsonListBodyHandler<>(AttemptResponseDTO.class));
    }

    public HttpResponse<List<LeaderboardRowDTO>> getLeaderboard() throws Exception {
        var getStats = HttpRequest.newBuilder(
                URI.create(BACKEND_HOST + "/leaders")
        ).GET().build();
        return httpClient.send(getStats, new JsonListBodyHandler<>(LeaderboardRowDTO.class));
    }

    static class JsonBodyHandler<T> implements HttpResponse.BodyHandler<T> {

        private final Class<T> clazz;

        public JsonBodyHandler(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {
            var stringBodySubscriber = HttpResponse.BodySubscribers
                    .ofString(StandardCharsets.UTF_8);

            return HttpResponse.BodySubscribers.mapping(
                    stringBodySubscriber,
                    (body) -> {
                        try {
                            return OBJECT_MAPPER.readValue(body, this.clazz);
                        } catch (JsonProcessingException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        }

    }

    static class JsonListBodyHandler<T> implements HttpResponse.BodyHandler<List<T>> {

        private final CollectionType mapCollectionType;

        public JsonListBodyHandler(Class<T> clazz) {
            this.mapCollectionType = OBJECT_MAPPER.getTypeFactory()
                    .constructCollectionType(List.class, clazz);
        }

        @Override
        public HttpResponse.BodySubscriber<List<T>> apply(HttpResponse.ResponseInfo responseInfo) {
            var stringBodySubscriber = HttpResponse.BodySubscribers
                    .ofString(StandardCharsets.UTF_8);

            return HttpResponse.BodySubscribers.mapping(
                    stringBodySubscriber,
                    (body) -> {
                        try {
                            return OBJECT_MAPPER.readValue(body, this.mapCollectionType);
                        } catch (JsonProcessingException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        }

    }

}
