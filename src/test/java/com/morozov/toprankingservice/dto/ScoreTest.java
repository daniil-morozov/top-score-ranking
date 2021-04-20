package com.morozov.toprankingservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ScoreTest {

  @Test
  public void Test_getters() {
    final UUID id = UUID.randomUUID();
    final String player = "player1";
    final Integer score = 100;
    final LocalDateTime time = LocalDateTime.now();

    final Score pojo = Mockito.mock(Score.class);
    Mockito.when(pojo.getId()).thenReturn(id);
    Mockito.when(pojo.getPlayer()).thenReturn(player);
    Mockito.when(pojo.getScore()).thenReturn(score);
    Mockito.when(pojo.getTime()).thenReturn(time);

    assertEquals(id, pojo.getId());
    assertEquals(player, pojo.getPlayer());
    assertEquals(score, pojo.getScore());
    assertEquals(time, pojo.getTime());
  }
}
