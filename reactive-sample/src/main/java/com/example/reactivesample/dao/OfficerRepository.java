package com.example.reactivesample.dao;

import com.example.reactivesample.entities.Officer;
import com.example.reactivesample.entities.Rank;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OfficerRepository {
  Flux<Officer> findByRank(Rank rank);

  Flux<Officer> findByLast(String last);

  Mono<Officer> save(Officer officer);

  Mono<Void> deleteAll();

  Flux<Officer> findAll();

  Mono<Officer> findById(String id);

  Mono<Integer> count();

  Mono<Void> deleteById(String id);
}
