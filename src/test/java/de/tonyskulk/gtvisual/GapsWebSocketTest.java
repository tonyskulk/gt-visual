package de.tonyskulk.gtvisual;

import java.net.URI;
import java.time.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GapsWebSocketTest {

  @LocalServerPort
  int randomServerPort;

  @Test
  public void streamGaps() throws InterruptedException {
    WebSocketClient webSocketClient = new ReactorNettyWebSocketClient();
    webSocketClient.execute(
        URI.create("ws://localhost:" + randomServerPort + "/gaps"),
        session -> session.send(Mono.just(session.textMessage("testMessage")))
            .thenMany(session.receive().map(WebSocketMessage::getPayloadAsText).log().take(2))
            .then()).blockOptional(Duration.ofSeconds(3));
  }

}
