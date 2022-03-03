package com.test.basics;

import reactor.core.publisher.Flux;

public class FluxRangeTrial {

  public static void main(String[] args) {
    Flux.range(10, 8)
        .map(num -> num + 3)
        .filter(num -> num % 2 == 0)
        .buffer(2)
        .flatMap(integers -> Flux.fromStream(integers.stream()))
        .subscribe(System.out::println);
  }
}
