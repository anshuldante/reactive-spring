package com.example.reactivesample.controllers;

import com.example.reactivesample.dao.OfficerRepository;
import com.example.reactivesample.entities.Officer;
import com.example.reactivesample.entities.Rank;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfficerControllerTest {

  @Autowired private WebTestClient client;
  @Autowired OfficerRepository repository;

  private static final List<Officer> OFFICERS =
      Arrays.asList(
          new Officer("1", Rank.CAPTAIN, "James", "Kirk"),
          new Officer("2", Rank.CAPTAIN, "Jean-Luc", "Picard"),
          new Officer("3", Rank.CAPTAIN, "Benjamin", "Sisko"),
          new Officer("4", Rank.CAPTAIN, "Kathryn", "Janeway"),
          new Officer("5", Rank.CAPTAIN, "Jonathan", "Archer"));

  @Before
  public void setup() {

    repository
        .deleteAll()
        .thenMany(Flux.fromIterable(OFFICERS))
        .flatMap(officer -> repository.save(officer))
        .doOnNext(System.out::println)
        .then()
        .block();
  }

  @Test
  public void getAllOfficersTest() {
    client
        .get()
        .uri("/officers")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(Officer.class)
        .hasSize(5)
        .consumeWith(System.out::println);
  }

  @Test
  public void getOfficerTest() {
    client
        .get()
        .uri("/officers/{id}", OFFICERS.get(0).getId())
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(Officer.class)
        .consumeWith(System.out::println);
  }

  @Test
  public void createOfficerTest() {
    client
        .post()
        .uri("/officers")
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(new Officer("7", Rank.ENSIGN, "Anshul", "Agrawal")), Officer.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.id")
        .isNotEmpty()
        .jsonPath("$.first")
        .isEqualTo("Anshul")
        .jsonPath("$.last")
        .isEqualTo("Agrawal")
        .consumeWith(System.out::println);
  }
}
