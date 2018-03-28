package de.tonyskulk.gtvisual;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

@SpringBootApplication
public class GtVisualApplication {

	@Bean
	public WebSocketHandlerAdapter wsha() {
		return new WebSocketHandlerAdapter();
	}

	@Bean
	public CountDownLatch closeLatch() {
		return new CountDownLatch(1);
	}

	@Bean
	public HandlerMapping webSocketHandlerMapping() {
		Map<String, WebSocketHandler> map = new HashMap<>();
		map.put("/gaps", gapsWebSocketHandler);

		SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
		handlerMapping.setOrder(1);
		handlerMapping.setUrlMap(map);
		return handlerMapping;
	}

	@Autowired
	private MappingMongoConverter mongoConverter;

	@Autowired
	private GapsWebSocketHandler gapsWebSocketHandler;

	// Converts . into a mongo friendly char
	@PostConstruct
	public void setUpMongoEscapeCharacterConversion() {
		mongoConverter.setMapKeyDotReplacement(",");
	}

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext ctx = SpringApplication.run(GtVisualApplication.class, args);

		final CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				closeLatch.countDown();
			}
		});
		closeLatch.await();
	}
}
