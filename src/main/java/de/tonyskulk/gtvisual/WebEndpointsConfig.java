package de.tonyskulk.gtvisual;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

@Configuration
public class WebEndpointsConfig {

  @Autowired
  private GapsWebSocketHandler gapsWebSocketHandler;

  @Bean
  public WebSocketHandlerAdapter wsha() {
    return new WebSocketHandlerAdapter();
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

  @Bean
  public RouterFunction<ServerResponse> monoRouterFunction(SymbolHandler symbolHandler) {
    return route(GET("/api/symbols"), symbolHandler::listSymbols);
  }

}
