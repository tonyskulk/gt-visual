package de.tonyskulk.gtvisual;

import java.net.URI;
import java.time.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GapsWebSocketTest {

  @Test
  public void streamGaps() throws InterruptedException {
    WebSocketClient webSocketClient = new ReactorNettyWebSocketClient();
    webSocketClient.execute(
        URI.create("ws://localhost:8080/gaps"),
        session -> session.send(Mono.just(session.textMessage("testMessage")))
            .thenMany(session.receive().map(WebSocketMessage::getPayloadAsText).log()).then())
        .block(Duration.ofSeconds(10L));
  }

}
