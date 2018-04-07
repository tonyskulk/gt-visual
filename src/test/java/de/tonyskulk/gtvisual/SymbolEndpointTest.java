package de.tonyskulk.gtvisual;

import java.time.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SymbolEndpointTest {

  @Autowired
  private WebTestClient webClient;

  @Test
  public void getSymbols() {
    this.webClient.mutate().responseTimeout(Duration.ofMinutes(2)).build().get()
        .uri("/api/symbols").accept(MediaType.APPLICATION_JSON).exchange()
        .expectBodyList(String.class)
        .consumeWith(result -> result.getResponseBody().forEach(System.out::println));

  }

}
