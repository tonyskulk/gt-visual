package de.tonyskulk.gtvisual;

import java.time.Duration;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class GapsWebSocketHandler implements WebSocketHandler {

  @Autowired
  private StreamGapRepository streamGapRepository;
  private ObjectMapper objectMapper = new ObjectMapper();

  public GapsWebSocketHandler() {
    objectMapper.registerModule(new JavaTimeModule());
    // objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  @Override
  public Mono<Void> handle(WebSocketSession webSocketSession) {
    System.out.println("enabling websocket session for gaps...");

    return webSocketSession.send(webSocketSession.receive()
        .map(WebSocketMessage::getPayloadAsText)
        .log()
        .flatMap(message -> Mono.delay(Duration.ofMillis(1000))
            .thenMany(output(webSocketSession, message))));
  }

  private Publisher<WebSocketMessage> output(WebSocketSession webSocketSession, String symbol) {
//    return streamGapRepository.findAll()// .takeLast(10)
    String basecurrency = symbol.substring(0, symbol.length() - 3);
    String counterCurrency = symbol.substring(symbol.length() - 3);
    return streamGapRepository.findByBaseCurrencyAndCounterCurrency(basecurrency, counterCurrency)
        .buffer(100).map(streamGap -> {
          try {
            return objectMapper.writeValueAsString(streamGap);
          } catch (JsonProcessingException e) {
            throw Exceptions.propagate(e);
          }
        }).map(webSocketSession::textMessage).delayElements(Duration.ofMillis(1000)).log();
  }
}
