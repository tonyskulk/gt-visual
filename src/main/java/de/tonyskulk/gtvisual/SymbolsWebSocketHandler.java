package de.tonyskulk.gtvisual;

import java.time.Duration;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
public class SymbolsWebSocketHandler implements WebSocketHandler {

  private StreamGapRepository streamGapRepository;

  @Autowired
  public SymbolsWebSocketHandler(StreamGapRepository streamGapRepository) {
    this.streamGapRepository = streamGapRepository;
  }

  @Override
  public Mono<Void> handle(WebSocketSession webSocketSession) {
    System.out.println("enabling websocket session for symbols");
    return webSocketSession.send(Mono.delay(Duration.ofMillis(1000)).thenMany(
        output(webSocketSession)));
  }

  private Publisher<WebSocketMessage> output(WebSocketSession webSocketSession) {
    return streamGapRepository.findAll()
        .map(streamGap -> streamGap.getBaseCurrency() + streamGap.getCounterCurrency()).distinct()
        .map(webSocketSession::textMessage);
  }
}
