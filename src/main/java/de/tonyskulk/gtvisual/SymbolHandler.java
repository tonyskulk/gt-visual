package de.tonyskulk.gtvisual;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SymbolHandler {

  private StreamGapRepository streamGapRepository;

  @Autowired
  public SymbolHandler(StreamGapRepository streamGapRepository) {
    this.streamGapRepository = streamGapRepository;
  }

  public Mono<ServerResponse> listSymbols(ServerRequest request) {
    Flux<String> symbols = streamGapRepository.findAll()
        .map(streamGap -> streamGap.getBaseCurrency() + streamGap.getCounterCurrency())
        .distinct();
//    Flux<String> symbols =
//        streamGapRepository.findDistinctByBaseCurrencyNotInAndCounterCurrencyNotIn(
//            Collections.emptyList(), Collections.emptyList())
//            .map(streamGap -> streamGap.getBaseCurrency() + "/" + streamGap.getCounterCurrency());

    return ok().contentType(APPLICATION_JSON).body(symbols, String.class);
  }
}
