package com.morozov.toprankingservice.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class AddScoreRequestTest {

  @Test
  public void Test_getters() {
    final String player = "player1";
    final Integer score = 100;
    final LocalDateTime time = LocalDateTime.now();

    final AddScoreRequest req = Mockito.mock(AddScoreRequest.class);

    Mockito.when(req.getPlayer()).thenReturn(player);
    Mockito.when(req.getScore()).thenReturn(score);
    Mockito.when(req.getTime()).thenReturn(time);

    assertEquals(player, req.getPlayer());
    assertEquals(score, req.getScore());
    assertEquals(time, req.getTime());
  }
}