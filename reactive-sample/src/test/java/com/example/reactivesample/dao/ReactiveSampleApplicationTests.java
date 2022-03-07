package com.example.reactivesample.dao;

import com.example.reactivesample.entities.Officer;
import com.example.reactivesample.entities.Rank;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactiveSampleApplicationTests {

  @Autowired private OfficerRepository repository;

  private static final List<Officer> OFFICERS =
      Arrays.asList(
          new Officer(Rank.CAPTAIN, "James", "Kirk"),
          new Officer(Rank.CAPTAIN, "Jean-Luc", "Picard"),
          new Officer(Rank.CAPTAIN, "Benjamin", "Sisko"),
          new Officer(Rank.CAPTAIN, "Kathryn", "Janeway"),
          new Officer(Rank.CAPTAIN, "Jonathan", "Archer"));

  @Before
  public void setup() {
    repository
        .deleteAll()
        .thenMany(Flux.fromIterable(OFFICERS))
        .flatMap(officer -> repository.save(officer))
        .then()
        .subscribe(unused -> System.out.println("DB created"));
  }

  @Test
  public void save() {
    StepVerifier.create(repository.save(new Officer(Rank.CAPTAIN, "Gabriel", "Lorca")))
        .expectNextMatches(officer -> !"".equalsIgnoreCase(officer.getId()))
        .verifyComplete();
  }

  @Test
  public void findAll() {
    StepVerifier.create(repository.findAll()).expectNextCount(5).verifyComplete();
  }

  @Test
  public void findById() {
    OFFICERS.stream()
        .map(Officer::getId)
        .forEach(
            id -> StepVerifier.create(repository.findById(id)).expectNextCount(1).verifyComplete());
  }

  @Test
  public void findByIdNonExistent() {
    StepVerifier.create(repository.findById("xyz")).verifyComplete();
  }

  @Test
  public void findCount() {
    StepVerifier.create(repository.count()).expectNextCount(1).verifyComplete();
  }

  @Test
  public void findByRank() {
    StepVerifier.create(repository.findByRank(Rank.CAPTAIN).map(Officer::getRank).distinct())
        .expectNextCount(1)
        .verifyComplete();

    StepVerifier.create(repository.findByRank(Rank.ENSIGN).map(Officer::getRank).distinct())
        .verifyComplete();
  }

  @Test
  public void findByLastName() {
    OFFICERS.stream()
        .map(Officer::getLast)
        .forEach(
            lastName ->
                StepVerifier.create(repository.findByLast(lastName))
                    .expectNextMatches(officer -> lastName.equalsIgnoreCase(officer.getLast()))
                    .verifyComplete());
  }
}
