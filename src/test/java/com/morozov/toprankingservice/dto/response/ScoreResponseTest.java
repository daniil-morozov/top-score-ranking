package com.morozov.toprankingservice.dto.response;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreResponseTest {
    @Test
    public void Test_getters() {
        final UUID id = UUID.randomUUID();
        final String player = "player1";
        final Integer score = 100;
        final LocalDateTime time = LocalDateTime.now();

        final ScoreResponse resp = Mockito.mock(ScoreResponse.class);

        Mockito.when(resp.getId()).thenReturn(id);
        Mockito.when(resp.getPlayer()).thenReturn(player);
        Mockito.when(resp.getScore()).thenReturn(score);
        Mockito.when(resp.getTime()).thenReturn(time);

        assertEquals(id, resp.getId());
        assertEquals(player, resp.getPlayer());
        assertEquals(score, resp.getScore());
        assertEquals(time, resp.getTime());
    }
}