import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.apache.catalina.connector.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import com.snakeinc.api.service.PlayerService.Player;
import com.snakeinc.api.service.PlayerService.PlayerParams;
import com.snakeinc.api.ApiApplication;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.ResponseEntity;
 
@SpringBootTest(classes = ApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlayerIntegrationTest {
 
    @Autowired
    private TestRestTemplate restTemplate;
 
    @Test
    public void testPostSuccessful() {
        PlayerParams params = new PlayerParams("John", 20);
        Player response = restTemplate.postForObject("/api/v1/players", params, Player.class);
        assert response != null;
        assert response.name().equals("John");
        assert response.age() == 20;
        assert response.category().equals("SENIOR");
    }

    @Test
    public void testPostValidationErrorAge() {
        PlayerParams params = new PlayerParams("Aurore", 5);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/players", params, String.class);
        assertTrue(response.getBody().contains("Age must be superior to 13"));
    }

    @Test
    public void testPostValidationErrorName() {
        PlayerParams params = new PlayerParams("", 20);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/players", params, String.class);
        assertTrue(response.getBody().contains("Name must be provided"));
    }
}