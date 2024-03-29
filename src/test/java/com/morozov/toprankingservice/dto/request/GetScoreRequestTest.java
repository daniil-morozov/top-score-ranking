package com.morozov.toprankingservice.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetScoreRequestTest {

  @Test
  public void Test_getters() {
    final List<String> players = List.of("player1");
    final LocalDateTime after = LocalDateTime.now();
    final LocalDateTime before = LocalDateTime.MAX;

    final GetScoreRequest pojo = Mockito.mock(GetScoreRequest.class);
    Mockito.when(pojo.getPlayers()).thenReturn(players);
    Mockito.when(pojo.getBefore()).thenReturn(before);
    Mockito.when(pojo.getAfter()).thenReturn(after);

    assertEquals(after, pojo.getAfter());
    assertEquals(before, pojo.getBefore());
    assertEquals(players, pojo.getPlayers());
  }
}
