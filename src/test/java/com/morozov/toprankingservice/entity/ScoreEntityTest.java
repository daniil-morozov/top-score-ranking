package com.morozov.toprankingservice.entity;

import com.morozov.toprankingservice.service.ScoreService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreEntityTest {
    @Test
    public void Test_getters() {
        final UUID id = UUID.randomUUID();
        final String player = "player1";
        final Integer score = 100;
        final LocalDateTime time = LocalDateTime.now();

        final ScoreEntity entity = Mockito.mock(ScoreEntity.class);
        Mockito.when(entity.getId()).thenReturn(id);
        Mockito.when(entity.getPlayer()).thenReturn(player);
        Mockito.when(entity.getScore()).thenReturn(score);
        Mockito.when(entity.getTime()).thenReturn(time);

        assertEquals(id, entity.getId());
        assertEquals(player, entity.getPlayer());
        assertEquals(score, entity.getScore());
        assertEquals(time, entity.getTime());
    }
}