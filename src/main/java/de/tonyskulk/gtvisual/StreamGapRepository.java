package de.tonyskulk.gtvisual;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StreamGapRepository extends ReactiveMongoRepository<StreamGap, String> {

}
