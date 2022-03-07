package com.example.reactivesample.dao;

import com.example.reactivesample.entities.Officer;
import com.example.reactivesample.entities.Rank;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class OfficerRepositoryImpl implements OfficerRepository {
  private static final Map<String, Officer> OFFICERS_MAP =
      Stream.of(
              new Officer(Rank.CAPTAIN, "James", "Kirk"),
              new Officer(Rank.CAPTAIN, "Jean-Luc", "Picard"),
              new Officer(Rank.CAPTAIN, "Benjamin", "Sisko"),
              new Officer(Rank.CAPTAIN, "Kathryn", "Janeway"),
              new Officer(Rank.CAPTAIN, "Jonathan", "Archer"))
          .collect(
              Collectors.toMap(
                  officer -> {
                    officer.setId(UUID.randomUUID().toString());
                    return officer.getId();
                  },
                  officer -> officer));

  @Override
  public Flux<Officer> findByRank(Rank rank) {
    return Flux.fromIterable(
        OFFICERS_MAP.values().stream()
            .filter(officer -> rank.equals(officer.getRank()))
            .collect(Collectors.toList()));
  }

  @Override
  public Flux<Officer> findByLast(String last) {
    return Flux.fromIterable(
        OFFICERS_MAP.values().stream()
            .filter(officer -> last.equalsIgnoreCase(officer.getLast()))
            .collect(Collectors.toList()));
  }

  @Override
  public Mono<Officer> save(Officer officer) {
    if (officer.getId() == null) {
      officer.setId(UUID.randomUUID().toString());
    }
    OFFICERS_MAP.put(officer.getId(), officer);

    return Mono.just(officer);
  }

  @Override
  public Mono<Void> deleteAll() {
    OFFICERS_MAP.clear();
    return Mono.empty();
  }

  @Override
  public Flux<Officer> findAll() {
    return Flux.fromIterable(OFFICERS_MAP.values());
  }

  @Override
  public Mono<Officer> findById(String id) {
    return Mono.justOrEmpty(Optional.ofNullable(OFFICERS_MAP.get(id)));
  }

  @Override
  public Mono<Integer> count() {
    return Mono.just(OFFICERS_MAP.size());
  }

  @Override
  public Mono<Void> deleteById(String id) {
    OFFICERS_MAP.remove(id);
    return Mono.empty();
  }
}
