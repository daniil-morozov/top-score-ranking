package com.morozov.toprankingservice.dto.request;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ScoreRequestTest {
    @Test
    public void Test_getters() {
        final String player = "player1";
        final Integer score = 100;
        final LocalDateTime time = LocalDateTime.now();

        final ScoreRequest req = Mockito.mock(ScoreRequest.class);

        Mockito.when(req.getPlayer()).thenReturn(player);
        Mockito.when(req.getScore()).thenReturn(score);
        Mockito.when(req.getTime()).thenReturn(time);

        assertEquals(player, req.getPlayer());
        assertEquals(score, req.getScore());
        assertEquals(time, req.getTime());
    }
}