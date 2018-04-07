package de.tonyskulk.gtvisual;

import java.util.List;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface StreamGapRepository extends ReactiveMongoRepository<StreamGap, String> {

  Flux<StreamGap> findDistinctByBaseCurrencyNotInAndCounterCurrencyNotIn(List<String> list1, List<String> list2);

  Flux<StreamGap> findByBaseCurrencyAndCounterCurrency(String basecurrency, String counterCurrency);
}
