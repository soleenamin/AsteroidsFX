package dk.sdu.cbse.score;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class ScoringService {

    private long totalScore = 0;

    public static void main(String[] args) {
        SpringApplication.run(ScoringService.class, args);
    }

    @GetMapping("/score")
    public Long addScore(@RequestParam("point") Long point) {
        totalScore += point;
        return totalScore;
    }
}
