package com.example.reactivesample.controllers;

import com.example.reactivesample.dao.OfficerRepository;
import com.example.reactivesample.entities.Officer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/officers")
public class OfficerController {

  @Autowired private OfficerRepository repository;

  @GetMapping
  public Flux<Officer> getAllOfficers() {
    return repository.findAll();
  }

  @GetMapping("{id}")
  public Mono<Officer> getOfficer(@PathVariable String id) {
    return repository.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Officer> addOfficer(@RequestBody Officer officer) {
    return repository.save(officer);
  }

  @PutMapping("{id}")
  public Mono<ResponseEntity<Officer>> updateOfficer(
      @PathVariable String id, @RequestBody Officer officer) {
    return repository
        .findById(id)
        .flatMap(
            existingOfficer -> {
              existingOfficer.setFirst(officer.getFirst());
              existingOfficer.setLast(officer.getLast());
              existingOfficer.setRank(officer.getRank());
              return repository.save(officer);
            })
        .map(ResponseEntity::ok)
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("{id}")
  public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
    return repository
        .deleteById(id)
        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping
  public Mono<Void> deleteAll() {
    return repository.deleteAll();
  }
}
