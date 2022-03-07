package com.example.reactivesample.entities;

import java.util.Objects;

public class Officer {

  private String id;
  private Rank rank;
  private String first;
  private String last;

  public Officer(String id, Rank rank, String first, String last) {
    this.id = id;
    this.rank = rank;
    this.first = first;
    this.last = last;
  }

  public Officer(Rank rank, String first, String last) {
    this.rank = rank;
    this.first = first;
    this.last = last;
  }

  public Officer() {}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Rank getRank() {
    return rank;
  }

  public void setRank(Rank rank) {
    this.rank = rank;
  }

  public String getFirst() {
    return first;
  }

  public void setFirst(String first) {
    this.first = first;
  }

  public String getLast() {
    return last;
  }

  public void setLast(String last) {
    this.last = last;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Officer officer = (Officer) o;
    return Objects.equals(id, officer.id)
        && Objects.equals(rank, officer.rank)
        && Objects.equals(first, officer.first)
        && Objects.equals(last, officer.last);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, rank, first, last);
  }
}
