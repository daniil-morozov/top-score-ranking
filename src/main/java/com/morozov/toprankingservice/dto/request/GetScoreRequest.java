package com.morozov.toprankingservice.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public class GetScoreRequest {

  private final LocalDateTime before;
  private final LocalDateTime after;
  private final List<String> players;

  @JsonCreator
  public GetScoreRequest(@JsonProperty("before") LocalDateTime before,
      @JsonProperty("after") LocalDateTime after,
      @JsonProperty("players") List<String> players) {
    this.before = before;
    this.after = after;
    this.players = players;
  }

  @JsonGetter
  public LocalDateTime getBefore() {
    return before;
  }

  @JsonGetter
  public LocalDateTime getAfter() {
    return after;
  }

  @JsonGetter
  public List<String> getPlayers() {
    return players;
  }
}
