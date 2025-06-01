package dk.sdu.cbse.core;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ScoringClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String scoringServiceUrl = "http://localhost:8081/score";

    public long sendScore(long points) {
        var uri = UriComponentsBuilder.fromHttpUrl(scoringServiceUrl)
                .queryParam("point", points)
                .toUriString();

        return restTemplate.getForObject(uri, Long.class);
    }
}
