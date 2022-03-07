package io.pivotal.literx;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;

/**
 * Learn how to create Flux instances.
 *
 * @author Sebastien Deleuze
 * @see <a
 *     href="https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html">Flux
 *     Javadoc</a>
 */
public class Part01Flux {

  // ========================================================================================

  Flux<String> emptyFlux() {
    return Flux.empty();
  }

  // ========================================================================================

  Flux<String> fooBarFluxFromValues() {
    return Flux.just("foo", "bar");
  }

  // ========================================================================================

  Flux<String> fooBarFluxFromList() {
    return Flux.fromIterable(Arrays.asList("foo", "bar"));
  }

  // ========================================================================================

  // TODO Create a Flux that emits an IllegalStateException
  Flux<String> errorFlux() {
    return Flux.error(new IllegalStateException());
  }

  // ========================================================================================

  // TODO Create a Flux that emits increasing values from 0 to 9 each 100ms
  Flux<Long> counter() {
    return Flux.range(0, 10)
        .buffer(Duration.ofMillis(100L))
        .flatMap(integers -> Flux.fromStream(integers.stream()))
        .map(Long.class::cast);
  }

  Flux<Long> counter2() {
    return Flux.interval(Duration.ofMillis(100)).take(10);
  }
}
