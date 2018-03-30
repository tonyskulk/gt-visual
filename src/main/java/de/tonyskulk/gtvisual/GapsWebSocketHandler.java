package de.tonyskulk.gtvisual;

import java.time.Duration;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

@Component
public class GapsWebSocketHandler implements WebSocketHandler {

	@Autowired
	private StreamGapRepository streamGapRepository;
	private ObjectMapper objectMapper = new ObjectMapper();

	public GapsWebSocketHandler() {
		objectMapper.registerModule(new JavaTimeModule());
//		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	@Override
	public Mono<Void> handle(WebSocketSession webSocketSession) {
		System.out.println("enabling websocket session");
		return webSocketSession.send(Mono.delay(Duration.ofMillis(1000)).thenMany(output(webSocketSession)));
	}

	private Publisher<WebSocketMessage> output(WebSocketSession webSocketSession) {
		return streamGapRepository.findAll()// .takeLast(10)
				.buffer(5)
				.map(streamGap -> {
					try {
						return objectMapper.writeValueAsString(streamGap);
					} catch (JsonProcessingException e) {
						throw Exceptions.propagate(e);
					}
				})
				.map(webSocketSession::textMessage)
				.delayElements(Duration.ofMillis(50))
				.log()
				;
	}
}
