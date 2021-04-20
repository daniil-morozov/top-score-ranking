package com.morozov.toprankingservice.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ScoreFilter {

  public static ScoreFilter EMPTY = new ScoreFilter(null, null, null);
  private final List<String> players;
  private final LocalDateTime before;
  private final LocalDateTime after;

  public ScoreFilter(List<String> players, LocalDateTime before, LocalDateTime after) {
    this.players = players;
    this.before = before;
    this.after = after;
  }

  public List<String> getPlayers() {
    return players;
  }

  public LocalDateTime getBefore() {
    return before;
  }

  public LocalDateTime getAfter() {
    return after;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScoreFilter filter = (ScoreFilter) o;
    return Objects.equals(players, filter.players) && Objects.equals(before, filter.before)
        && Objects.equals(after, filter.after);
  }

  @Override
  public int hashCode() {
    return Objects.hash(players, before, after);
  }
}
